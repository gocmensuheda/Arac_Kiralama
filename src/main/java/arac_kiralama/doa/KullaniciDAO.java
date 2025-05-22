package arac_kiralama.doa;

import arac_kiralama.models.Kullanici;
import arac_kiralama.util.DatabaseUtil;
import arac_kiralama.util.SifrelemeUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KullaniciDAO {

    // Kullanıcıyı veritabanına ekleme (Şifre hashlenmiş şekilde)
    public void kullaniciEkle(String email, String sifre, String rol, int yas) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String hashliSifre = SifrelemeUtil.sifreleSHA256(sifre); // Şifreyi hashle

            String sql = "INSERT INTO kullanicilar2 (email, sifre, rol, yas) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, hashliSifre);
            pstmt.setString(3, rol);
            pstmt.setInt(4, yas); // Yaş bilgisini ekledik
            pstmt.executeUpdate();

            System.out.println("Kullanıcı başarıyla eklendi!");
        } catch (SQLException e) {
            throw new RuntimeException("Kullanıcı ekleme sırasında hata!", e);
        }
    }

    // Kullanıcı giriş işlemi (SHA-256 ile doğrulama)
    public Kullanici girisYap(String email, String sifre) {
        String sql = "SELECT sifre, rol, yas FROM kullanicilar2 WHERE email = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String kayitliHashliSifre = rs.getString("sifre");
                String girilenSifreHash = SifrelemeUtil.sifreleSHA256(sifre);
                System.out.println("Giriş sırasında üretilen hash: " + girilenSifreHash);
                System.out.println("Veritabanında kayıtlı hash: " + kayitliHashliSifre);
// Kullanıcının girdiği şifreyi hashle

                if (kayitliHashliSifre.equals(girilenSifreHash)) {
                    System.out.println("Giriş başarılı!");
                    return new Kullanici(email, kayitliHashliSifre, rs.getString("rol"));
                } else {
                    System.out.println("Hatalı şifre!");
                }
            } else {
                System.out.println("Kullanıcı bulunamadı!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Giriş işlemi sırasında hata!", e);
        }
        return null;
    }

    // Admin kullanıcı ekleme
    public void yeniAdminEkle() {
        kullaniciEkle("admin@example.com", "gizliSifre", "ADMIN", 35); // Admin eklenirken yaş bilgisi giriyoruz
    }
}
