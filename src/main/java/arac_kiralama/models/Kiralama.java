package arac_kiralama.models;
import java.time.LocalDateTime;

import java.util.Objects;

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
        setBaslangicTarihi(baslangicTarihi);
        setBitisTarihi(bitisTarihi);
        setKiralamaTipi(kiralamaTipi);
    }

    // Getter ve Setter metotları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("ID negatif veya 0 olamaz!");
        }
    }

    public Kullanici getMusteri() {
        return musteri;
    }

    public void setMusteri(Kullanici musteri) {
        if (musteri != null) {
            this.musteri = musteri;
        } else {
            throw new IllegalArgumentException("Müşteri bilgisi boş olamaz!");
        }
    }

    public Arac getArac() {
        return arac;
    }

    public void setArac(Arac arac) {
        if (arac != null) {
            this.arac = arac;
        } else {
            throw new IllegalArgumentException("Araç bilgisi boş olamaz!");
        }
    }

    public LocalDateTime getBaslangicTarihi() {
        return baslangicTarihi;
    }

    public void setBaslangicTarihi(LocalDateTime baslangicTarihi) {
        if (baslangicTarihi.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Başlangıç tarihi geçmişte olamaz!");
        }
        this.baslangicTarihi = baslangicTarihi;
    }

    public LocalDateTime getBitisTarihi() {
        return bitisTarihi;
    }

    public void setBitisTarihi(LocalDateTime bitisTarihi) {
        if (bitisTarihi.isBefore(baslangicTarihi)) {
            throw new IllegalArgumentException("Bitiş tarihi, başlangıç tarihinden önce olamaz!");
        }
        this.bitisTarihi = bitisTarihi;
    }

    public String getKiralamaTipi() {
        return kiralamaTipi;
    }

    public void setKiralamaTipi(String kiralamaTipi) {
        if (kiralamaTipi.equalsIgnoreCase("Saatlik") ||
                kiralamaTipi.equalsIgnoreCase("Günlük") ||
                kiralamaTipi.equalsIgnoreCase("Haftalık") ||
                kiralamaTipi.equalsIgnoreCase("Aylık")) {
            this.kiralamaTipi = kiralamaTipi;
        } else {
            throw new IllegalArgumentException("Geçersiz kiralama tipi!");
        }
    }

    // Nesnenin ekrana yazdırılmasını kolaylaştıran toString metodu
    @Override
    public String toString() {
        return "Kiralama { " +
                "ID=" + id +
                ", Müşteri=" + musteri.getEmail() +
                ", Araç=" + arac.getMarka() + " " + arac.getModel() +
                ", Başlangıç Tarihi=" + baslangicTarihi +
                ", Bitiş Tarihi=" + bitisTarihi +
                ", Kiralama Tipi='" + kiralamaTipi + '\'' +
                " }";
    }

    // Nesne karşılaştırmaları için equals ve hashCode metotları
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kiralama)) return false;
        Kiralama kiralama = (Kiralama) o;
        return id == kiralama.id &&
                Objects.equals(musteri, kiralama.musteri) &&
                Objects.equals(arac, kiralama.arac) &&
                Objects.equals(baslangicTarihi, kiralama.baslangicTarihi) &&
                Objects.equals(bitisTarihi, kiralama.bitisTarihi) &&
                Objects.equals(kiralamaTipi, kiralama.kiralamaTipi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, musteri, arac, baslangicTarihi, bitisTarihi, kiralamaTipi);
    }
}
