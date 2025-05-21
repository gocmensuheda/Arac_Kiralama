package arac_kiralama.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/Arac-kiralama";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres"; // Şifreni güncelle

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Veritabanı bağlantısı kurulamadı!", e);
        }
    }
}