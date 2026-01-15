import java.security.MessageDigest;

public class PasswordBuild {

    public static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }


    public static boolean verifyPassword(String plainPassword, String encryptedPassword) {
        String encrypted = encryptPassword(plainPassword);
        return encrypted != null && encrypted.equals(encryptedPassword);
    }
}