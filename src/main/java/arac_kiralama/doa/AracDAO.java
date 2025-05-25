package arac_kiralama.dao;

import arac_kiralama.models.Arac;
import arac_kiralama.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AracDAO {

    // 1️⃣ Araç Ekleme (Transaction Yönetimi Eklendi)
    public void aracEkle(Arac arac) {
        String sql = "INSERT INTO arac (marka, model, kategori, kiralama_ucreti, bedel) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false); // Transaction başlat

            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, arac.getMarka());
                pstmt.setString(2, arac.getModel());
                pstmt.setString(3, arac.getKategori());
                pstmt.setDouble(4, arac.getKiralamaUcreti());
                pstmt.setDouble(5, arac.getBedel());

                pstmt.executeUpdate();
                conn.commit(); // İşlemi tamamla
                System.out.println("✅ Araç başarıyla eklendi: " + arac.getMarka() + " " + arac.getModel());
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Araç ekleme sırasında hata oluştu!", e);
        }
    }

    // 2️⃣ Araç Listesi Getirme (Sayfalama ile)
    public List<Arac> araclariGetir(int sayfa, int sayfaBoyutu) {
        List<Arac> aracListesi = new ArrayList<>();
        int offset = (sayfa - 1) * sayfaBoyutu;
        String sql = "SELECT * FROM arac ORDER BY id ASC LIMIT ? OFFSET ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sayfaBoyutu);
            pstmt.setInt(2, offset);
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
            throw new RuntimeException("❌ Araçları getirirken hata oluştu!", e);
        }
        return aracListesi;
    }

    // 3️⃣ Kategoriye Göre Araç Filtreleme
    public List<Arac> kategoriyeGoreAracGetir(String kategori) {
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
            throw new RuntimeException("❌ Kategoriye göre araç getirme hatası!", e);
        }
        return aracListesi;
    }

    // 4️⃣ ID'ye Göre Araç Getirme (Eksik metod eklendi!)
    public Arac getAracById(int aracId) {
        String sql = "SELECT * FROM arac WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, aracId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Arac(
                        rs.getInt("id"),
                        rs.getString("marka"),
                        rs.getString("model"),
                        rs.getString("kategori"),
                        rs.getDouble("kiralama_ucreti"),
                        rs.getDouble("bedel")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Araç ID ile getirme işlemi sırasında hata oluştu!", e);
        }
        return null; // Eğer araç bulunamazsa null döner
    }

    // 5️⃣ Araç Kiralanabilir mi? (Geliştirilmiş Koşul Kontrolleri)
    public boolean aracKiralanabilirMi(int aracId, int yas, double depozito) {
        String sql = "SELECT bedel FROM arac WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, aracId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double aracBedeli = rs.getDouble("bedel");

                if (aracBedeli > 2_000_000) {
                    return yas >= 30 && depozito >= aracBedeli * 0.10;
                }
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Araç kiralanabilir mi kontrolü sırasında hata oluştu!", e);
        }
        return false;
    }
}
