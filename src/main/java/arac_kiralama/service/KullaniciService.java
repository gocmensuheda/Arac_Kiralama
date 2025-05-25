package arac_kiralama.service;




import arac_kiralama.doa.KullaniciDAO;
import arac_kiralama.models.Kullanici;
import arac_kiralama.util.SifrelemeUtil;

public class KullaniciService {
    private KullaniciDAO kullaniciDAO = new KullaniciDAO();

    // Kullanıcı giriş yapma
    public Kullanici girisYap(String email, String sifre) {
        String sifreHash = SifrelemeUtil.sifreleSHA256(sifre); // Email, salt olarak kullanıldı
        Kullanici kullanici = kullaniciDAO.girisYap(email, sifreHash);

        if (kullanici == null) {
            System.out.println("Hatalı giriş! Kullanıcı adı veya şifre yanlış.");
        }

        return kullanici;
    }
}