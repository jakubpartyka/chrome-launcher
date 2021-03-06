package launcher.data;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {
    private static String lastPassword;

    public static boolean hashMatch(String input, String originalHash){
        String hashed = encryptSHA256(input);
        if(hashed == null)
            return false;
        return hashed.equals(originalHash);
    }

    public static String encryptSHA256(String originalString) {
        if(originalString.isBlank())
            return "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e){
            return null;
        }
    }

    public static void setLastPassword(String lastPassword) {
        Encryptor.lastPassword = lastPassword;
    }

    public static String getLastPassword() {
        return lastPassword;
    }
}
