package arac_kiralama.util;

public class testSifre {
    public static void main(String[] args) {
        String sifre1 = "admin123"; // Admin için şifre
        String sifre2 = "musteri123"; // Müşteri için şifre

        // Hashlenmiş şifreleri üret
        String hashliSifre1 = SifrelemeUtil.sifreleSHA256(sifre1);
        String hashliSifre2 = SifrelemeUtil.sifreleSHA256(sifre2);

        // Sonuçları ekrana yazdır
        System.out.println("Admin hashli şifre: " + hashliSifre1);
        System.out.println("Müşteri hashli şifre: " + hashliSifre2);


    }
}
