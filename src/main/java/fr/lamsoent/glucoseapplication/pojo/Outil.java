package fr.lamsoent.glucoseapplication.pojo;

import org.mindrot.jbcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Outil {
    public static String hashPassWordBcrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verifyPasswordBcrypt(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}