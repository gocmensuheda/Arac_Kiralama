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

    public void kiralamaEkle(Kiralama kiralama) {
        String sql = "INSERT INTO kiralama (kullanici_id, arac_id, kiralama_tipi, baslangic_tarihi, bitis_tarihi, depozito, toplam_ucret) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false); // ✅ Transaction başlat

            // ✅ Kiralama ücreti ve depozito hesapla
            double hesaplananUcret = kiralamaUcretiHesapla(kiralama);
            double hesaplananDepozito = depozitoHesapla(kiralama);

            kiralama.setKiralamaUcreti(hesaplananUcret);
            kiralama.setDepozito(hesaplananDepozito);

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, kiralama.getMusteri().getId());
                pstmt.setInt(2, kiralama.getArac().getId());
                pstmt.setString(3, kiralama.getKiralamaTipi());
                pstmt.setTimestamp(4, Timestamp.valueOf(kiralama.getBaslangicTarihi()));
                pstmt.setTimestamp(5, Timestamp.valueOf(kiralama.getBitisTarihi()));
                pstmt.setDouble(6, kiralama.getDepozito());
                pstmt.setDouble(7, kiralama.getKiralamaUcreti());

                pstmt.executeUpdate();
                conn.commit(); // ✅ İşlem başarılı, commit

                System.out.println("✅ Kiralama başarıyla tamamlandı! Ücret: " + hesaplananUcret + " TL, Depozito: " + hesaplananDepozito + " TL");
            } catch (SQLException e) {
                conn.rollback(); // ❌ Hata olursa geri al
                System.out.println("❌ Kiralama işlemi başarısız oldu! Hata detayı: " + e.getMessage());
                throw new RuntimeException("❌ Kiralama işlemi sırasında hata oluştu! Geri alındı.", e);
            }

        } catch (SQLException e) {
            throw new RuntimeException("❌ Veritabanı bağlantı hatası!", e);
        }
    }

    // ✅ Kiralama ücreti hesaplama metodu
    public double kiralamaUcretiHesapla(Kiralama kiralama) {
        double ucret = kiralama.getArac().getKiralamaUcreti();

        if (kiralama.getKiralamaTipi().equalsIgnoreCase("Saatlik")) {
            return ucret * 1;
        } else if (kiralama.getKiralamaTipi().equalsIgnoreCase("Gunluk")) {
            return ucret * 24;
        } else if (kiralama.getKiralamaTipi().equalsIgnoreCase("Haftalik")) {
            return ucret * 7 * 24;
        } else if (kiralama.getKiralamaTipi().equalsIgnoreCase("Aylik")) {
            return ucret * 30 * 24;
        }
        return 1000; // Varsayılan hata önleme değeri
    }

    // ✅ Depozito hesaplama metodu
    public double depozitoHesapla(Kiralama kiralama) {
        double aracBedeli = kiralama.getArac().getBedel();

        if (aracBedeli > 2000000) {
            return aracBedeli * 0.10; // 2 milyon üzeri için %10 depozito
        } else {
            return kiralamaUcretiHesapla(kiralama) * 2; // Diğer araçlar için depozito
        }
    }

    // ✅ Kullanıcının Kiralama Geçmişini Getirme
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
                        rs.getDouble("toplam_ucret")
                );
                kiralamaListesi.add(kiralama);
            }
        } catch (SQLException e) {
            throw new RuntimeException("❌ Kiralama geçmişi getirme hatası!", e);
        }
        return kiralamaListesi;
    }
}
