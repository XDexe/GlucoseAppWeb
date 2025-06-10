package fr.lamsoent.glucoseapplication.controller;
import jakarta.annotation.Resource;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.ejb.Stateless;

import java.io.Serializable;


@Stateless
public class EmailController implements Serializable {

    @Resource(lookup = "Default") // Adapter selon ton serveur
    private Session mailSession;

    public void sendEmail(String to, String subject, String body) {
        try {
            Message message = new MimeMessage(mailSession);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email envoyé avec succès !");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}