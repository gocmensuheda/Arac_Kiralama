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

    // 1️⃣ Kiralama Ekleme (Transaction Yönetimi ile)
    public void kiralamaEkle(Kiralama kiralama) {
        String sql = "INSERT INTO kiralama (musteri_email, arac_id, baslangic_tarihi, bitis_tarihi, kiralama_tipi) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false); // Transaction başlat

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, kiralama.getMusteri().getEmail());
                pstmt.setInt(2, kiralama.getArac().getId());
                pstmt.setTimestamp(3, Timestamp.valueOf(kiralama.getBaslangicTarihi()));
                pstmt.setTimestamp(4, Timestamp.valueOf(kiralama.getBitisTarihi()));
                pstmt.setString(5, kiralama.getKiralamaTipi());

                pstmt.executeUpdate();
                conn.commit(); // İşlemi tamamla
                System.out.println("Araç başarıyla kiralandı!");
            } catch (SQLException e) {
                conn.rollback(); // Hata olursa geri al
                throw new RuntimeException("Kiralama işlemi sırasında hata oluştu!", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Veritabanı bağlantı hatası!", e);
        }
    }

    // 2️⃣ Kullanıcının Kiralama Geçmişini Getirme
    public List<Kiralama> kiralamaGecmisiGetir(String musteriEmail) {
        List<Kiralama> kiralamaListesi = new ArrayList<>();
        String sql = "SELECT k.id, a.marka, a.model, k.baslangic_tarihi, k.bitis_tarihi, k.kiralama_tipi FROM kiralama k " +
                "JOIN arac a ON k.arac_id = a.id WHERE k.musteri_email = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, musteriEmail);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Kullanici musteri = new Kullanici(musteriEmail, "", "MUSTERI"); // Şifreyi getirmiyoruz
                Arac arac = new Arac(0, rs.getString("marka"), rs.getString("model"), "", 0, 0);
                Kiralama kiralama = new Kiralama(
                        rs.getInt("id"),
                        musteri,
                        arac,
                        rs.getTimestamp("baslangic_tarihi").toLocalDateTime(),
                        rs.getTimestamp("bitis_tarihi").toLocalDateTime(),
                        rs.getString("kiralama_tipi")
                );
                kiralamaListesi.add(kiralama);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Kiralama geçmişi getirme hatası!", e);
        }
        return kiralamaListesi;
    }

    // 3️⃣ Araç Kiralanabilir mi? (Kurallara Göre Kontrol)
    public boolean aracKiralanabilirMi(int aracId, int yas, double depozito) {
        String sql = "SELECT bedel FROM arac WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, aracId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double aracBedeli = rs.getDouble("bedel");

                if (aracBedeli > 2000000) {
                    if (yas < 30) {
                        return false; // 30 yaşından küçükse kiralayamaz
                    }
                    if (depozito < (aracBedeli * 0.10)) {
                        return false; // Yeterli depozito yatırılmamışsa kiralayamaz
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Araç kiralanabilir mi kontrolü sırasında hata oluştu!", e);
        }
        return false;
    }
}