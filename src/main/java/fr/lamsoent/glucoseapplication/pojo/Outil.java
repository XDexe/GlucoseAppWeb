package fr.lamsoent.glucoseapplication.pojo;

import org.mindrot.jbcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Outil {
    public static String hashPassWord(String password) {
        try {
            // Obtenir une instance de l'algorithme de hashage SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Convertir le mot de passe en bytes
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            // Retourner le mot de passe haché en Base64 pour plus de lisibilité
            return Base64.getEncoder().encodeToString(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public static String hashPassWordBcrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verifyPasswordBcrypt(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

}