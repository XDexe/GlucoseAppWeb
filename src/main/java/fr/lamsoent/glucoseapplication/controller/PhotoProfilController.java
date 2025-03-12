package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.pojo.Dieteticien;
import fr.lamsoent.glucoseapplication.pojo.Entraineur;
import fr.lamsoent.glucoseapplication.pojo.Medecin;
import fr.lamsoent.glucoseapplication.pojo.Personne;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import java.io.*;

@Named
@ViewScoped
public class PhotoProfilController {
    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        FacesContext context = FacesContext.getCurrentInstance();

        // Retrieve logged in user via PersonneController
        PersonneController personneController = context.getApplication()
                .evaluateExpressionGet(context, "#{personneController}", PersonneController.class);
        Personne user = personneController.getPersonneLogin();

        // Base folder for profile photos
        String relativePath = "photo-profil";

        // Determine sub-folder based on the user type
        if (user instanceof Dieteticien) {
            relativePath += "/dieteticien";
        } else if (user instanceof Medecin) {
            relativePath += "/medecin";
        } else if (user instanceof Entraineur) {
            relativePath += "/entraineur";
        } else {
            relativePath += "/utilisateur";
        }

        // Get absolute path for designated folder
        String absolutePath = context.getExternalContext().getRealPath(relativePath);
        File uploadDir = new File(absolutePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Ensure the file name is safe for saving
        File file = new File(uploadDir, new File(uploadedFile.getFileName()).getName());
        try (InputStream input = uploadedFile.getInputstream();
             OutputStream output = new FileOutputStream(file)) {
            IOUtils.copy(input, output);
        } catch (IOException ex) {
            ex.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Upload failed", ex.getMessage()));
            return;
        }

        context.addMessage(null, new FacesMessage("Successful",
                uploadedFile.getFileName() + " was uploaded to " + relativePath));
    }
}
