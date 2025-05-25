package arac_kiralama.doa;

import arac_kiralama.models.Arac;
import arac_kiralama.models.Kiralama;
import arac_kiralama.models.Kullanici;
import arac_kiralama.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class KiralamaDAO {

    // 1️⃣ Kiralama Ekleme (Doğrulamalar artık Service katmanında!)
    public void kiralamaEkle(Kiralama kiralama) {
        String sql = "INSERT INTO kiralama (kullanici_id, arac_id, baslangic_tarihi, bitis_tarihi, kiralama_tipi, depozito, toplam_ucret) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, kiralama.getMusteri().getId());
            pstmt.setInt(2, kiralama.getArac().getId());
            pstmt.setTimestamp(3, Timestamp.valueOf(kiralama.getBaslangicTarihi()));
            pstmt.setTimestamp(4, Timestamp.valueOf(kiralama.getBitisTarihi()));
            pstmt.setString(5, kiralama.getKiralamaTipi());
            pstmt.setDouble(6, kiralama.getDepozito());
            pstmt.setDouble(7, kiralama.getKiralamaUcreti()); // Güncellenmiş ücret alanı

            pstmt.executeUpdate();
            System.out.println("✅ Kiralama tamamlandı! Toplam Ücret: " + kiralama.getKiralamaUcreti() + " TL");
        } catch (SQLException e) {
            throw new RuntimeException("❌ Kiralama işlemi sırasında hata oluştu!", e);
        }
    }

    // 2️⃣ Kullanıcının Kiralama Geçmişini Getirme
    public List<Kiralama> kiralamaGecmisiGetir(int kullaniciId) {
        List<Kiralama> kiralamaListesi = new ArrayList<>();
        String sql = "SELECT k.id, a.marka, a.model, k.baslangic_tarihi, k.bitis_tarihi, k.kiralama_tipi, k.depozito, k.toplam_ucret FROM kiralama k " +
                "JOIN arac a ON k.arac_id = a.id WHERE k.kullanici_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, kullaniciId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Kullanici musteri = new Kullanici(kullaniciId, "", "", "MUSTERI", 25, 1000.0);
                Arac arac = new Arac(0, rs.getString("marka"), rs.getString("model"), "", 0, 0);

                Kiralama kiralama = new Kiralama(
                        rs.getInt("id"),
                        musteri,
                        arac,
                        rs.getTimestamp("baslangic_tarihi").toLocalDateTime(),
                        rs.getTimestamp("bitis_tarihi").toLocalDateTime(),
                        rs.getString("kiralama_tipi"),
                        rs.getDouble("depozito"),
                        rs.getDouble("toplam_ucret") // Yeni eklenen alan
                );
                kiralamaListesi.add(kiralama);
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Kiralama geçmişi getirme hatası!", e);
        }
        return kiralamaListesi;
    }

    // 3️⃣ Araç Kiralanabilir Mi? (Yaş ve Depozito Kontrolü)
    public boolean aracKiralanabilirMi(int aracId, int yas, double depozito) {
        String sql = "SELECT bedel FROM arac WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, aracId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double aracBedeli = rs.getDouble("bedel");

                if (aracBedeli > 2_000_000) {
                    return (yas >= 30) && (depozito >= aracBedeli * 0.10);
                }
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Araç kiralanabilir mi kontrolü sırasında hata oluştu!", e);
        }
        return false;
    }
}
