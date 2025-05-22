package arac_kiralama.util;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class SifrelemeUtil {

    // SHA-256 ile şifreleme (Salt olmadan)
    public static String sifreleSHA256(String sifre) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(sifre.getBytes(StandardCharsets.UTF_8)); // UTF-8 güvenli şekilde kullanıldı

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b)); // Hash'i HEX formatına çeviriyoruz
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 şifreleme hatası!", e);
        }
    }

    // Test amaçlı çalıştırma metodu
    public static void main(String[] args) {
        String testSifre = "admin123";
        System.out.println("Test Şifre Hash: " + sifreleSHA256(testSifre));
    }
}
