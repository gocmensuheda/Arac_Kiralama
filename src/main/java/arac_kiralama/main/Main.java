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
