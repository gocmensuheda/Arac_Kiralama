package arac_kiralama.doa;

import arac_kiralama.models.Arac;
import arac_kiralama.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AracDAO {

    // 1. Araç Ekleme
    public void aracEkle(Arac arac) {
        String sql = "INSERT INTO arac (marka, model, kategori, kiralama_ucreti, bedel) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, arac.getMarka());
            pstmt.setString(2, arac.getModel());
            pstmt.setString(3, arac.getKategori());
            pstmt.setDouble(4, arac.getFiyat());
            pstmt.setDouble(5, arac.getDepozito());

            pstmt.executeUpdate();
            System.out.println("Araç başarıyla eklendi: " + arac.getMarka() + " " + arac.getModel());
        } catch (SQLException e) {
            throw new RuntimeException("Araç ekleme sırasında hata oluştu!", e);
        }
    }

    // 2. Araç Listesi Getirme
    public List<Arac> araclariGetir() {
        List<Arac> aracListesi = new ArrayList<>();
        String sql = "SELECT * FROM arac ORDER BY id ASC";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Arac arac = new Arac(
                        rs.getInt("id"),
                        rs.getString("marka"),
                        rs.getString("model"),
                        rs.getString("kategori"),
                        rs.getDouble("kiralama_ucreti"),
                        rs.getDouble("bedel")
                );
                aracListesi.add(arac);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Araçları getirirken hata oluştu!", e);
        }
        return aracListesi;
    }

    // 3. Kategoriye Göre Araç Filtreleme
    public List<Arac> kategoriyeGoreAraçGetir(String kategori) {
        List<Arac> aracListesi = new ArrayList<>();
        String sql = "SELECT * FROM arac WHERE kategori = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kategori);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Arac arac = new Arac(
                        rs.getInt("id"),
                        rs.getString("marka"),
                        rs.getString("model"),
                        rs.getString("kategori"),
                        rs.getDouble("kiralama_ucreti"),
                        rs.getDouble("bedel")
                );
                aracListesi.add(arac);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Kategoriye göre araç getirme hatası!", e);
        }
        return aracListesi;
    }

    // 4. Araç Kiralanabilir mi?
    public boolean aracKiralanabilirMi(int aracId, int yas, double depozito) {
        String sql = "SELECT bedel FROM arac WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, aracId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double aracBedeli = rs.getDouble("bedel");
                if (aracBedeli > 2000000 && yas < 30) {
                    return false; // 30 yaşından küçükse kiralayamaz
                }
                if (aracBedeli > 2000000 && depozito < (aracBedeli * 0.10)) {
                    return false; // Yeterli depozito yatırılmamışsa kiralayamaz
                }
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Araç kiralanabilir mi kontrolü sırasında hata oluştu!", e);
        }
        return false;
    }
}
