package fr.lamsoent.glucoseapplication.controller;
import fr.lamsoent.glucoseapplication.model.ActiviteModel;
import fr.lamsoent.glucoseapplication.pojo.Activite;
import fr.lamsoent.glucoseapplication.pojo.Activite;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class ActiviteController implements Serializable {

    @EJB
    private ActiviteModel activiteModel;

    @Inject
    private DonneeController donneeController;

    private Activite activite = new Activite();

    public void editActivite() {
        activiteModel.update(this.activite);
        activite = new Activite();
    }

    public void deleteActivite(Activite activite) {
        activiteModel.delete(activite);
    }

    public String accesActivite(Activite activite) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("idActivite", activite.getId());
        return "graphiqueClient.xhtml?faces-redirect=true";
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public List<Activite> getActivites() {
        return activiteModel.read();
    }

    public void resetForm() {
        this.activite = new Activite();
    }

    public ActiviteModel getActiviteModel() {
        return activiteModel;
    }

    public void setActiviteModel(ActiviteModel activiteModel) {
        this.activiteModel = activiteModel;
    }

    public void GraphiqueClientController() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Object idParam = session.getAttribute("idActivite");
            if (idParam != null) {
                this.activite.setId((int) idParam);
            }
        }
    }
}
