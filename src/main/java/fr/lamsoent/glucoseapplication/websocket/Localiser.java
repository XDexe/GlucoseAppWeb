package fr.lamsoent.glucoseapplication.websocket;

import fr.lamsoent.glucoseapplication.model.ActiviteModel;
import fr.lamsoent.glucoseapplication.model.CapteurModel;
import fr.lamsoent.glucoseapplication.pojo.Activite;
import fr.lamsoent.glucoseapplication.pojo.Capteur;
import fr.lamsoent.glucoseapplication.pojo.Donnee;
import fr.lamsoent.glucoseapplication.pojo.Personne;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ServerEndpoint(value = "/websocketlocate")
@Singleton
@ApplicationScoped
public class Localiser {
    private List<Session> anonyme = new ArrayList<>();
    @EJB
    private ActiviteModel activiteModel;

    @EJB
    private CapteurModel capteurModel;

    @OnOpen
    public void onOpen(Session session) {
        if(!anonyme.contains(session)) {
            anonyme.add(session);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        String[] args = message.split("#");
        switch (args[0]){
            case "geolocaliser":
                sendMessage(getAllCapteurs(), session);
            default:
                break;
        }
    }

    @OnClose
    public void onClose(Session session) {
        anonyme.remove(session);

    }

    public List<Capteur> getAllCapteurs(){
        return capteurModel.read();
    }



    @OnError
    public void onError(Session session, Throwable throwable) {
    }

    public void sendMessage(Capteur capteur){

        String dataString;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            dataString = jsonb.toJson(capteur);
            for(Session s : anonyme) {
                s.getAsyncRemote().sendText(dataString);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la sérialisation des données : " + e.getMessage());
            e.printStackTrace();
            return;
        }



    }

    public void sendMessage(List<Capteur> listCap, Session session){

        try (Jsonb jsonb = JsonbBuilder.create()) {
            String dataString = jsonb.toJson(listCap);
            session.getAsyncRemote().sendText(dataString);
        } catch (Exception e) {
            System.err.println("Erreur lors de la sérialisation des données : " + e.getMessage());
            e.printStackTrace();
        }
    }

}

