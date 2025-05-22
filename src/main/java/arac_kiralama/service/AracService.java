package arac_kiralama.service;

import java.util.List;

import arac_kiralama.doa.AracDAO;
import arac_kiralama.models.Arac;
import arac_kiralama.expection.YetkisizIslemException;
import arac_kiralama.models.Kullanici;

public class AracService {
    private AracDAO aracDAO = new AracDAO();

    // Araçları Sayfalama ile Listeleme
    public List<Arac> araclariListele(int sayfa, int sayfaBoyutu) {
        return aracDAO.araclariGetir(sayfa, sayfaBoyutu);
    }

    // Yeni Araç Ekleme
    public void aracEkle(Arac arac, Kullanici kullanici) {
        // Sadece ADMIN kullanıcıları araç ekleyebilir
        if (!kullanici.getRol().equals("ADMIN")) {
            throw new YetkisizIslemException("Sadece ADMIN kullanıcıları araç ekleyebilir!");
        }

        aracDAO.aracEkle(arac);
        System.out.println("Araç başarıyla eklendi!");
    }

    // Kategoriye Göre Araç Arama
    public List<Arac> kategoriyeGoreAra(String kategori) {
        return aracDAO.kategoriyeGoreAracGetir(kategori);
    }
}
