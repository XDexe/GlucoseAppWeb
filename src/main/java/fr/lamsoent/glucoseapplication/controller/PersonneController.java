package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.PersonneModel;
import fr.lamsoent.glucoseapplication.pojo.Entraineur;
import fr.lamsoent.glucoseapplication.pojo.Personne;
import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
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

    public void saveImage(Personne personne) {
        System.out.println("Entré méthode save image");
        if (uploadedFile != null) {

            System.out.println("Uploaded file name: " + uploadedFile.getFileName());

            if (personne == null) {
                personne = personneModel.read(1);
            }
            String fileName = uploadedFile.getFileName();
            String oldFileName = personne.getPhotoDeProfilUrl();

            if (fileName == null || fileName.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Veuillez choisir une image"));
                return;
            }
            String[] list = fileName.split("\\.");
            if (list.length < 2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Veuillez choisir une image"));
                return;
            }
            String extension = list[list.length - 1];
            fileName = personne.getIdPersonne() + "_" + UUID.randomUUID() + "_" + extension;

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

                // Met à jour le nom de fichier dans la base de données.
                personne.setPhotoDeProfilUrl(fileName);
                personneModel.update(personne);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Image téléchargée avec succès !"));

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public String AuthPersonne() {
        System.out.println("Authentification en cours");
        listPersonne = personneModel.read();

        boolean isLogged = false;

        for (Personne p : listPersonne) {
            if (p.getIdentifiant() == null || p.getIdentifiant().isEmpty() || p.getMotDePasse() == null || p.getMotDePasse().isEmpty()) {
                System.out.println("id personne " + p.getIdPersonne());
                continue;
            }
            if (p.getIdentifiant().equals(personneLogin.getIdentifiant()) && p.getMotDePasse().equals(personneLogin.getMotDePasse())) {
                personneLogin = p;
                isLogged = true;
                break;
            }
        }

        if (isLogged) {
            System.out.println("Authentification Réussi");
            if (personneLogin.getClass().equals(Utilisateur.class)) {
                Utilisateur utilisateur = (Utilisateur) personneLogin;
                utilisateurController.setUtilisateur(utilisateur);
                System.out.println(utilisateurController.getUtilisateur().getIdPersonne());
                return "listeActivites?faces-redirect=true";
            }
        } else {
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
