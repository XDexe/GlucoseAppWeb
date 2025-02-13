package fr.lamsoent.glucoseapplication.websocket;

import fr.lamsoent.glucoseapplication.model.ActiviteModel;
import fr.lamsoent.glucoseapplication.pojo.Activite;
import fr.lamsoent.glucoseapplication.pojo.Donnee;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ServerEndpoint("/websocketdata")
@Singleton
@ApplicationScoped
public class Graphique {

    private List<Session> anonyme = new ArrayList<>();
    private HashMap<Integer, List<Session>> clientAbonnes = new HashMap<>();
    @EJB
    private ActiviteModel activiteModel;


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
            case "abonnement":
                abonnementClient(args, session);
            default:
                break;
        }


    }

    @OnClose
    public void onClose(Session session) {
        anonyme.remove(session);
        clientAbonnes.values().forEach(sessions -> {
            if (sessions.contains(session)){
                sessions.remove(session);
            }
        });

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
    }

    public void sendMessage(Donnee donnee){
        Activite activite = donnee.getActivite();

        String dataString;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            dataString = jsonb.toJson(donnee);
        } catch (Exception e) {
            System.err.println("Erreur lors de la sérialisation des données : " + e.getMessage());
            e.printStackTrace();
            return;
        }

        if(!clientAbonnes.get(activite.getId()).isEmpty()){
            clientAbonnes.get(activite.getId()).forEach(session -> {
                session.getAsyncRemote().sendText(dataString);
            });
        }
    }


    public void abonnementClient(String[] args,Session session){
        int idActivite;
        try {
            idActivite = Integer.parseInt(args[1]);
        }catch (NumberFormatException  e){
            e.printStackTrace();
            return;
        }
        if(activiteModel.read(idActivite) == null) {
              return;
        }

        if(!clientAbonnes.containsKey(idActivite)){
            clientAbonnes.put(idActivite, new ArrayList<Session>());
        }

        if (!clientAbonnes.get(idActivite).contains(session)){
            clientAbonnes.get(idActivite).add(session);
        }

    }

}
