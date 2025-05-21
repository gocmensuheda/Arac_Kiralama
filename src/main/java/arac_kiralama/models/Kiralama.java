package arac_kiralama.models;
import java.time.LocalDateTime;

public class Kiralama {
    private int id;
    private Kullanici musteri;
    private Arac arac;
    private LocalDateTime baslangicTarihi;
    private LocalDateTime bitisTarihi;
    private String kiralamaTipi; // "Saatlik", "Günlük", "Haftalık", "Aylık"

    public Kiralama(int id, Kullanici musteri, Arac arac, LocalDateTime baslangicTarihi, LocalDateTime bitisTarihi, String kiralamaTipi) {
        this.id = id;
        this.musteri = musteri;
        this.arac = arac;
        this.baslangicTarihi = baslangicTarihi;
        this.bitisTarihi = bitisTarihi;
        this.kiralamaTipi = kiralamaTipi;
    }

    // Getter ve Setter metotları

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Kullanici getMusteri() {
        return musteri;
    }

    public void setMusteri(Kullanici musteri) {
        this.musteri = musteri;
    }

    public LocalDateTime getBaslangicTarihi() {
        return baslangicTarihi;
    }

    public void setBaslangicTarihi(LocalDateTime baslangicTarihi) {
        this.baslangicTarihi = baslangicTarihi;
    }

    public Arac getArac() {
        return arac;
    }

    public void setArac(Arac arac) {
        this.arac = arac;
    }

    public LocalDateTime getBitisTarihi() {
        return bitisTarihi;
    }

    public void setBitisTarihi(LocalDateTime bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
    }

    public String getKiralamaTipi() {
        return kiralamaTipi;
    }

    public void setKiralamaTipi(String kiralamaTipi) {
        this.kiralamaTipi = kiralamaTipi;
    }
}