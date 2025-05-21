package arac_kiralama.main;


import arac_kiralama.doa.AracDAO;
import arac_kiralama.models.Arac;

import java.util.List;
import java.util.Scanner;

public class AracKiralamaApp {
    private static Scanner scanner = new Scanner(System.in);
    private static AracDAO aracDAO = new AracDAO();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Araç Kiralama Sistemi ===");
            System.out.println("1. Araçları Listele");
            System.out.println("2. Araç Ekle");
            System.out.println("3. Kategoriye Göre Araç Ara");
            System.out.println("0. Çıkış");
            System.out.print("Seçiminizi yapın: ");

            int secim = scanner.nextInt();
            scanner.nextLine(); // Enter tuşunu temizle

            switch (secim) {
                case 1:
                    araclariListele();
                    break;
                case 2:
                    aracEkle();
                    break;
                case 3:
                    kategoriyeGoreAra();
                    break;
                case 0:
                    System.out.println("Çıkış yapılıyor...");
                    System.exit(0);
                default:
                    System.out.println("Geçersiz seçim, tekrar deneyin.");
            }
        }
    }

    private static void araclariListele() {
        List<Arac> aracListesi = aracDAO.araclariGetir();
        for (Arac arac : aracListesi) {
            System.out.println(arac);
        }
    }

    private static void aracEkle() {
        System.out.print("Marka: ");
        String marka = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Kategori (Otomobil, Helikopter, Motosiklet): ");
        String kategori = scanner.nextLine();
        System.out.print("Kiralama Ücreti: ");
        double kiralamaUcreti = scanner.nextDouble();
        System.out.print("Araç Bedeli: ");
        double bedel = scanner.nextDouble();
        scanner.nextLine(); // Enter tuşunu temizle

        Arac yeniArac = new Arac(0, marka, model, kategori, kiralamaUcreti, bedel);
        aracDAO.aracEkle(yeniArac);
        System.out.println("Araç başarıyla eklendi.");
    }

    private static void kategoriyeGoreAra() {
        System.out.print("Hangi kategoride araç arıyorsunuz? ");
        String kategori = scanner.nextLine();
        List<Arac> filtrelenmisAraclar = aracDAO.kategoriyeGoreAraçGetir(kategori);

        if (filtrelenmisAraclar.isEmpty()) {
            System.out.println("Bu kategoride araç bulunamadı.");
        } else {
            for (Arac arac : filtrelenmisAraclar) {
                System.out.println(arac);
            }
        }
    }
}
