package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.PersonneModel;
import fr.lamsoent.glucoseapplication.pojo.*;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.eclipse.persistence.oxm.annotations.XmlNamedAttributeNode;
import org.primefaces.model.file.UploadedFile;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Named
@SessionScoped
public class PersonneController implements Serializable {
    private List<Personne> listPersonne = new ArrayList<Personne>();
    private Personne personneLogin = new Personne();
    private UploadedFile uploadedFile;

    @Inject
    private UtilisateurController utilisateurController;

    @EJB
    private PersonneModel personneModel;

    @Named
    @Inject
    private MedecinController medecinController;

    @Named
    @Inject
    private EntraineurController entraineurController;

    public void saveImage(Personne personne) {
        if (uploadedFile != null) {
            String fileName = uploadedFile.getFileName();
            String oldFileName = personne.getPhotoDeProfilUrl();

            if (fileName == null || fileName.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Veuillez choisir une image"));
                return;
            }
            String[] list = fileName.split("\\.");
            if (list.length < 2) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Veuillez choisir une image"));
                return;
            }
            String extension = list[list.length - 1];
            fileName = "img_" + UUID.randomUUID() + "." + extension;

            if (!(extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png"))) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Format d'image non supporté.", null));
                return;
            }
            String uploadPath = FacesContext.getCurrentInstance().getExternalContext()
                    .getRealPath("/resources/photo-profil") + File.separator + fileName;
            String deletePath = FacesContext.getCurrentInstance().getExternalContext()
                    .getRealPath("/resources/photo-profil") + File.separator + oldFileName;
            try {
                File destinationFile = new File(uploadPath);
                Files.copy(uploadedFile.getInputStream(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                if (oldFileName != null && !oldFileName.isEmpty() && !oldFileName.equals("default.png")) {
                    File deleteDestinationFile = new File(deletePath);
                    if (deleteDestinationFile.exists()) {
                        Files.delete(deleteDestinationFile.toPath());
                    }
                }
                // Update the picture URL
                personne.setPhotoDeProfilUrl(fileName);
                // Persist the change into the database
                personneModel.update(personne);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Image téléchargée avec succès !"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteImage(Personne personne){
        String oldFileName = personne.getPhotoDeProfilUrl();

        if(oldFileName == null || oldFileName.isEmpty() || oldFileName.equals("default.png")){
            return;
        }

        String deletePath = FacesContext.getCurrentInstance().getExternalContext()
                .getRealPath("/resources/photo-profil") + File.separator + oldFileName;
        try {
            if (oldFileName != null && !oldFileName.isEmpty() && !oldFileName.equals("default.png")) {
                File deleteDestinationFile = new File(deletePath);
                if (deleteDestinationFile.exists()) {
                    Files.delete(deleteDestinationFile.toPath());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String AuthPersonne() {
        System.out.println("Authentification en cours");
        listPersonne = personneModel.read();

        System.out.println("personne login " + personneLogin.getPlainTextPassword());
        System.out.println("personne login " + personneLogin.getIdentifiant());
        boolean isLogged = false;
        for (Personne p : listPersonne) {
            if (p.getIdentifiant() == null || p.getIdentifiant().isEmpty() || p.getMotDePasse() == null || p.getMotDePasse().isEmpty()) {
                continue;}
            boolean resultpass = Outil.verifyPasswordBcrypt(personneLogin.getPlainTextPassword(), p.getMotDePasse());
            System.out.println("resultpass " + resultpass);
            if (p.getIdentifiant().equals(personneLogin.getIdentifiant()) && resultpass) {
                personneLogin = p;
                isLogged = true;
                break;
            }}
        if (isLogged) {
            System.out.println("Authentification Réussi");
            if (personneLogin.getClass().equals(Utilisateur.class)) {
                Utilisateur utilisateur = (Utilisateur) personneLogin;
                utilisateurController.setUtilisateur(utilisateur);
                System.out.println(utilisateurController.getUtilisateur().getIdPersonne());
                return "client/listeActivites?faces-redirect=true";
            } else if (personneLogin.getClass().equals(Medecin.class)) {
                Medecin medecin = (Medecin) personneLogin;
                medecinController.setMedecin(medecin);
                return "admin/gestionMedecin?faces-redirect=true";}
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Identifiant ou mot de passe incorrect"));
            System.out.println("Authentification échoué");
        }
        return "";
    }

    public List<Personne> getListPersonne() {
        return listPersonne;
    }

    public void setListPersonne(List<Personne> listPersonne) {
        this.listPersonne = listPersonne;
    }

    public Personne getPersonneLogin() {
        return personneLogin;
    }

    public void setPersonneLogin(Personne personneLogin) {
        this.personneLogin = personneLogin;
    }

    public PersonneModel getPersonneModel() {
        return personneModel;
    }

    public void setPersonneModel(PersonneModel personneModel) {
        this.personneModel = personneModel;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

}
