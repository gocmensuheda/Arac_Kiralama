package arac_kiralama.service;

import arac_kiralama.doa.KiralamaDAO;
import arac_kiralama.models.Kiralama;
import arac_kiralama.models.Kullanici;
import arac_kiralama.models.Arac;
import arac_kiralama.expection.YetkisizIslemException;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class KiralamaService {
    private KiralamaDAO kiralamaDAO = new KiralamaDAO();
    private AracService aracService = new AracService(); // AracService çağrıldı

    //  Kiralama Yapma (Yetki Kontrolü ve Araç Uygunluğu)
    public void kiralamaYap(Kiralama kiralama, Kullanici kullanici) {
        Arac arac = kiralama.getArac();

        // Kullanıcı rolü kontrolü (Sadece müşteriler kiralama yapabilir)
        if (!kullanici.getRol().equalsIgnoreCase("MUSTERI")) {
            throw new YetkisizIslemException(" Sadece müşteriler araç kiralayabilir!");
        }

        //  Kurumsal kullanıcılar için minimum 1 aylık kiralama şartı
        long kiralamaSuresiGun = ChronoUnit.DAYS.between(kiralama.getBaslangicTarihi(), kiralama.getBitisTarihi());
        if (kullanici.isKurumsal() && kiralamaSuresiGun < 30) {
            throw new RuntimeException(" Kurumsal kullanıcılar en az 1 aylık kiralama yapmalıdır.");
        }

        //  2M TL üzerindeki araçlar için yaş kontrolü ve depozito hesaplama
        double depozito = 0;
        if (arac.getBedel() > 2_000_000) {
            if (kullanici.getYas() < 30) {
                throw new RuntimeException(" 30 yaşından küçük kullanıcılar, bedeli 2M TL üzeri olan araçları kiralayamaz.");
            }
            depozito = arac.getBedel() * 0.1; // %10 depozito ekleniyor
        }

        //  Kiralama ücretinin araç kategorisine göre belirlenmesi (AracService kullanılarak çağrıldı!)
        double kiralamaUcreti = aracService.getKiralamaUcretiByKategori(arac.getKategori());

        // Güncellenmiş kiralama nesnesini oluştur
        kiralama.setKiralamaUcreti(kiralamaUcreti);
        kiralama.setDepozito(depozito);

      /* //  Araç kiralanabilir mi kontrolü
        boolean uygunMu = kiralamaDAO.aracKiralanabilirMi(arac.getId(), kullanici.getYas(), depozito);
        if (!uygunMu) {
            throw new RuntimeException(" Araç kiralamaya uygun değil!");
        }*/

        // Kiralama işlemini veritabanına kaydet
        kiralamaDAO.kiralamaEkle(kiralama);
        System.out.println(" Kiralama tamamlandı! Ücret: " + kiralamaUcreti + " TL, Depozito: " + depozito);
    }

    //  Kullanıcının Kiralama Geçmişini Getirme
    public List<Kiralama> kiralamaGecmisiGetir(int kullaniciId) {
        return kiralamaDAO.kiralamaGecmisiGetir(kullaniciId);
    }
}
