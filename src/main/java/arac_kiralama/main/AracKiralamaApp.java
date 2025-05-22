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
    private static Kullanici aktifKullanici = null; // Sistemde giriş yapmış kullanıcı
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Araç Kiralama Sistemine Hoş Geldiniz!");

        while (true) {
            if (aktifKullanici == null) {
                girisYap();
            } else {
                anaMenu();
            }
        }
    }

    private static void girisYap() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Şifre: ");
        String sifre = scanner.nextLine();

        aktifKullanici = kullaniciService.girisYap(email, sifre);

        if (aktifKullanici == null) {
            System.out.println("Hatalı giriş! Tekrar deneyiniz.");
        } else {
            System.out.println("Giriş başarılı. Hoş geldin, " + aktifKullanici.getEmail());
        }
    }

    private static void anaMenu() {
        System.out.println("\nAna Menü:");
        System.out.println("1 - Araçları Listele");
        System.out.println("2 - Araç Kirala");
        System.out.println("3 - Kiralama Geçmişi");
        System.out.println("4 - Çıkış Yap");
        System.out.print("Seçiminiz: ");

        int secim = scanner.nextInt();
        scanner.nextLine();

        switch (secim) {
            case 1:
                aracListele();
                break;
            case 2:
                aracKirala();
                break;
            case 3:
                kiralamaGecmisiGoruntule();
                break;
            case 4:
                cikisYap();
                break;
            default:
                System.out.println("Geçersiz seçim! Lütfen tekrar deneyin.");
        }
    }

    private static void aracListele() {
        System.out.println("\nAraçlar:");
        List<Arac> araclar = aracService.araclariListele(1, 10); // İlk 10 araç
        for (Arac arac : araclar) {
            System.out.println(arac);
        }
    }

    private static void aracKirala() {
        System.out.print("Kiralamak istediğiniz araç ID: ");
        int aracId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Başlangıç tarihi (YYYY-MM-DD HH:mm): ");
        LocalDateTime baslangicTarihi = LocalDateTime.parse(scanner.nextLine().replace(" ", "T"));
        System.out.print("Bitiş tarihi (YYYY-MM-DD HH:mm): ");
        LocalDateTime bitisTarihi = LocalDateTime.parse(scanner.nextLine().replace(" ", "T"));
        System.out.print("Kiralama Tipi (Saatlik/Günlük/Haftalık/Aylık): ");
        String kiralamaTipi = scanner.nextLine();

        Arac arac = new Arac(aracId, "", "", "", 0, 0); // Basit nesne oluşturduk (Gerçek veritabanı nesnesi alınmalı)
        Kiralama kiralama = new Kiralama(0, aktifKullanici, arac, baslangicTarihi, bitisTarihi, kiralamaTipi);

        try {
            kiralamaService.kiralamaYap(kiralama, aktifKullanici);
            System.out.println("Kiralama başarıyla tamamlandı!");
        } catch (Exception e) {
            System.out.println("Kiralama işlemi başarısız! " + e.getMessage());
        }
    }

    private static void kiralamaGecmisiGoruntule() {
        System.out.println("\nKiralama Geçmişiniz:");
        List<Kiralama> gecmis = kiralamaService.kiralamaGecmisiGetir(aktifKullanici.getEmail());
        for (Kiralama kiralama : gecmis) {
            System.out.println(kiralama);
        }
    }

    private static void cikisYap() {
        System.out.println("Çıkış yapılıyor...");
        aktifKullanici = null;
    }
}
