package arac_kiralama.doa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import arac_kiralama.models.Kullanici;
import arac_kiralama.util.DatabaseUtil;

public class KullaniciDAO {
    public Kullanici girisYap(String email, String sifre) {
        Kullanici kullanici = null;
        try (Connection con = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM kullanicilar WHERE email = ? AND sifre = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, sifre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                kullanici = new Kullanici(rs.getString("email"), rs.getString("sifre"), rs.getString("rol"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kullanici;
    }
}