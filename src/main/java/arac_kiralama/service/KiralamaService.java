package arac_kiralama.service;

import arac_kiralama.doa.KiralamaDAO;
import arac_kiralama.models.Kiralama;
import arac_kiralama.models.Kullanici;
import arac_kiralama.expection.YetkisizIslemException;
import java.util.List;

public class KiralamaService {
    private KiralamaDAO kiralamaDAO = new KiralamaDAO();

    // 1️⃣ Kiralama Yapma
    public void kiralamaYap(Kiralama kiralama, Kullanici kullanici) {
        if (!kullanici.getRol().equals("MUSTERI")) {
            throw new YetkisizIslemException("Sadece müşteriler araç kiralayabilir!");
        }

        boolean uygunMu = kiralamaDAO.aracKiralanabilirMi(kiralama.getArac().getId(), kullanici.getYas(), kiralama.getArac().getDepozito());
        if (!uygunMu) {
            throw new RuntimeException("Araç kiralamaya uygun değil!");
        }


        kiralamaDAO.kiralamaEkle(kiralama);
        System.out.println("Araç başarıyla kiralandı!");
    }

    // 2️⃣ Kullanıcının Kiralama Geçmişini Getirme
    public List<Kiralama> kiralamaGecmisiGetir(String musteriEmail) {
        return kiralamaDAO.kiralamaGecmisiGetir(musteriEmail);
    }
}

