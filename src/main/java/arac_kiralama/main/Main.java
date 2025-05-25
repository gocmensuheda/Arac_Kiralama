package arac_kiralama.main;

import arac_kiralama.util.DatabaseUtil;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            if (conn != null) {
                System.out.println("Veritabanına başarılı şekilde bağlanıldı!");
            } else {
                System.out.println("Veritabanı bağlantısı başarısız!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        AracKiralamaApp.main(args);
    }
}
/********* VERİ TABANINDAKİ KULLANICILARIN BİLGİLERİ*************
 *
 1-admin.example.com  Şifre-> admin123

 2-musteri@examplen.com   Şifre-> musteri123

 3-musteri2@example.com   Şifre->musteri2

 4-kurumsal@example.com  Şifre->kurumsal123



 */