package arac_kiralama.service;

import java.util.List;
import arac_kiralama.models.Arac;
import arac_kiralama.expection.YetkisizIslemException;
import arac_kiralama.models.Kullanici;

public class AracService {
    public arac_kiralama.doa.AracDAO aracDAO = new arac_kiralama.doa.AracDAO();

    //  Araçları Listeleme
    public List<Arac> araclariListele(int sayfa, int sayfaBoyutu) {
        return aracDAO.araclariGetir(sayfa, sayfaBoyutu);
    }

    // Yeni Araç Ekleme
    public void aracEkle(Arac arac, Kullanici kullanici) {
        if (!kullanici.getRol().equalsIgnoreCase("ADMIN")) {
            throw new YetkisizIslemException(" Sadece ADMIN kullanıcıları araç ekleyebilir!");
        }
        aracDAO.aracEkle(arac);
        System.out.println(" Araç başarıyla eklendi!");
    }

    //  Kategoriye Göre Araç Arama
    public List<Arac> kategoriyeGoreAra(String kategori) {
        return aracDAO.kategoriyeGoreAracGetir(kategori);
    }

    //  ID'ye Göre Araç Getirme
    public Arac getAracById(int aracId) {
        return aracDAO.getAracById(aracId);
    }

    // Araç Kategorisine Göre Kiralama Ücreti Belirleme (Taşındı!)
    public double getKiralamaUcretiByKategori(String kategori) {
        return switch (kategori.toLowerCase()) {
            case "otomobil" -> 1000;      // Otomobiller için 1000 TL
            case "helikopter" -> 25000;   // Helikopterler için 25.000 TL
            case "motosiklet" -> 600;     // Motosikletler için 600 TL
            default -> 800;               // Varsayılan ücret
        };
    }
}
