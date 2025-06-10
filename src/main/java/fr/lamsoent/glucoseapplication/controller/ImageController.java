package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.PersonneModel;
import fr.lamsoent.glucoseapplication.pojo.Personne;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.UUID;

@Named
@SessionScoped
public class ImageController implements Serializable {

    private UploadedFile uploadedFile;
    private String tempImagePath;

    @EJB
    private PersonneModel personneModel;

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

    public void deleteImage(Personne personne) {
        String oldFileName = personne.getPhotoDeProfilUrl();

        if (oldFileName == null || oldFileName.isEmpty() || oldFileName.equals("default.png")) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetUploadedFile() {
        this.uploadedFile = null;
    }

    public String getTempImagePath() {
        return tempImagePath;
    }

    public void setTempImagePath(String tempImagePath) {
        this.tempImagePath = tempImagePath;
    }

    public void handleFileUpload(FileUploadEvent event) {
        System.out.println("Method called - handleFileUpload");
        if (event == null) {
            System.out.println("Event is null");
            return;
        }
        if (event.getFile() == null) {
            System.out.println("File is null");
            return;
        }

        this.uploadedFile = event.getFile();
        try {
            // Rest of your code...
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Success", "Image téléchargée: " + uploadedFile.getFileName()));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public boolean hasUploadedFile() {
        return uploadedFile != null;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public PersonneModel getPersonneModel() {
        return personneModel;
    }

    public void setPersonneModel(PersonneModel personneModel) {
        this.personneModel = personneModel;
    }


}
