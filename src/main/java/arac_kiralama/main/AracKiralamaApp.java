package arac_kiralama.main;

import arac_kiralama.models.Kullanici;
import arac_kiralama.models.Arac;
import arac_kiralama.models.Kiralama;
import arac_kiralama.service.KullaniciService;
import arac_kiralama.service.AracService;
import arac_kiralama.service.KiralamaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class AracKiralamaApp {
    private static KullaniciService kullaniciService = new KullaniciService();
    private static AracService aracService = new AracService();
    private static KiralamaService kiralamaService = new KiralamaService();
    private static Kullanici aktifKullanici = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(" Araç Kiralama Sistemine Hoş Geldiniz!");

        while (true) {
            if (aktifKullanici == null) {
                girisYap();
            } else {
                if (aktifKullanici.getRol().equalsIgnoreCase("ADMIN")) {
                    adminMenu();
                } else {
                    kullaniciMenu();
                }
            }
        }
    }

    private static void girisYap() {
        System.out.print(" Email: ");
        String email = scanner.nextLine().trim();
        System.out.print(" Şifre: ");
        String sifre = scanner.nextLine().trim();

        if (email.isEmpty() || sifre.isEmpty()) {
            System.out.println(" Hatalı giriş! Email ve şifre boş olamaz.");
            return;
        }

        aktifKullanici = kullaniciService.girisYap(email, sifre);

        if (aktifKullanici == null) {
            System.out.println(" Hatalı giriş! Tekrar deneyiniz.");
        } else {
            System.out.println(" Giriş başarılı. Hoş geldiniz, " + aktifKullanici.getEmail());
        }
    }

    private static void kategoriBazliAracListele() {
        System.out.print("🔍 Kategoriye göre araç aramak için kategori girin: ");
        String kategori = scanner.nextLine().trim();

        List<Arac> aracListesi = aracService.kategoriyeGoreAra(kategori);

        if (aracListesi.isEmpty()) {
            System.out.println(" Bu kategoride araç bulunamadı!");
        } else {
            System.out.println("\n **Kategori Bazlı Araçlar:**");
            for (Arac arac : aracListesi) {
                System.out.println(arac);
            }
        }
    }


    private static void adminMenu() {
        System.out.println("\n **Admin Menü:**");
        System.out.println("1️ - Araçları Listele");
        System.out.println("2️ - Araç Ekle");
        System.out.println("3️ - Çıkış Yap");

        System.out.print("Seçiminiz: ");
        int secim = scanner.nextInt();
        scanner.nextLine();

        switch (secim) {
            case 1 -> aracListele();
            case 2 -> aracEkle();
            case 3 -> cikisYap();
            default -> System.out.println(" Geçersiz seçim! Lütfen tekrar deneyin.");
        }
    }

    private static void kullaniciMenu() {
        System.out.println("\n **Kullanıcı Menü:**");
        System.out.println("1️ - Araçları Listele");
        System.out.println("2️ - Kategori Bazlı Araç Listele");
        System.out.println("2️ - Araç Kirala");
        System.out.println("3️ - Kiralama Geçmişi");
        System.out.println("4️ - Çıkış Yap");

        System.out.print("Seçiminiz: ");
        int secim = scanner.nextInt();
        scanner.nextLine();

        switch (secim) {
            case 1 -> aracListele();
            case 2 -> kategoriBazliAracListele();
            case 3 -> aracKirala();
            case 4 -> kiralamaGecmisiGoruntule();
            case 5 -> cikisYap();
            default -> System.out.println(" Geçersiz seçim! Lütfen tekrar deneyin.");
        }
    }

    private static void aracKirala() {
        System.out.print(" Kiralamak istediğiniz araç ID: ");
        int aracId = scanner.nextInt();
        scanner.nextLine();

        System.out.print(" Başlangıç tarihi (YYYY-MM-DD HH:mm): ");
        LocalDateTime baslangicTarihi = LocalDateTime.parse(scanner.nextLine().replace(" ", "T"));
        System.out.print(" Bitiş tarihi (YYYY-MM-DD HH:mm): ");
        LocalDateTime bitisTarihi = LocalDateTime.parse(scanner.nextLine().replace(" ", "T"));

        Arac arac = aracService.getAracById(aracId);

        if (arac == null) {
            System.out.println(" Geçersiz araç ID! Lütfen tekrar deneyin.");
            return;
        }

        System.out.print(" Kiralama Tipi (Saatlik/Günlük/Haftalık/Aylık): ");
        String kiralamaTipi = scanner.nextLine().trim();
// Kullanıcının girdiği değeri veritabanındaki format ile eşleşecek şekilde düzelt
        if (kiralamaTipi.equalsIgnoreCase("Günlük")) {
            kiralamaTipi = "Gunluk"; // Veritabanının kabul ettiği format
        } else if (kiralamaTipi.equalsIgnoreCase("Haftalık")) {
            kiralamaTipi = "Haftalik";
        } else if (kiralamaTipi.equalsIgnoreCase("Aylık")) {
            kiralamaTipi = "Aylik";
        }


// Kullanıcı geçersiz bir değer girerse hata almamak için kontrol ekleyelim
        if (!kiralamaTipi.equalsIgnoreCase("Saatlik") &&
                !kiralamaTipi.equalsIgnoreCase("Gunluk") &&
                !kiralamaTipi.equalsIgnoreCase("Haftalık") &&
                !kiralamaTipi.equalsIgnoreCase("Aylık")) {
            System.out.println(" Geçersiz kiralama tipi! Lütfen geçerli bir seçenek girin.");
            System.out.println("Veritabanına gidecek kiralama_tipi: " + kiralamaTipi); // Terminalde kontrol için ekleme
            return;
        }

        Kiralama kiralama = new Kiralama(0, aktifKullanici, arac, baslangicTarihi, bitisTarihi, kiralamaTipi, 0, 0);

        try {
            kiralamaService.kiralamaYap(kiralama, aktifKullanici);
            System.out.println(" Kiralama başarıyla tamamlandı!");
        } catch (Exception e) {
            System.out.println(" Kiralama işlemi başarısız! " + e.getMessage());
        }
    }

    private static void kiralamaGecmisiGoruntule() {
        System.out.println("\n **Kiralama Geçmişiniz:**");
        List<Kiralama> gecmis = kiralamaService.kiralamaGecmisiGetir(aktifKullanici.getId());
        for (Kiralama kiralama : gecmis) {
            System.out.println(kiralama);
        }
    }

    private static void aracEkle() {
        if (!aktifKullanici.getRol().equalsIgnoreCase("ADMIN")) {
            System.out.println("Bu işlemi sadece ADMIN kullanıcılar yapabilir!");
            return;
        }

        System.out.print("Marka: ");
        String marka = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Kategori: ");
        String kategori = scanner.nextLine();
        System.out.print("Kiralama Ücreti: ");
        double kiralamaUcreti = scanner.nextDouble();
        System.out.print("Bedel: ");
        double bedel = scanner.nextDouble();
        scanner.nextLine();

        Arac yeniArac = new Arac(0, marka, model, kategori, kiralamaUcreti, bedel);

        try {
            aracService.aracEkle(yeniArac, aktifKullanici);
            System.out.println(" Araç başarıyla eklendi: " + marka + " " + model);
        } catch (Exception e) {
            System.out.println(" Araç ekleme işlemi başarısız! " + e.getMessage());
        }
    }

    private static void aracListele() {
        System.out.println("\n **Araçlar:**");
        List<Arac> araclar = aracService.araclariListele(1, 10);
        for (Arac arac : araclar) {
            System.out.println(arac);
        }
    }

    private static void cikisYap() {
        System.out.println(" Çıkış yapılıyor...");
        aktifKullanici = null;
    }
}

