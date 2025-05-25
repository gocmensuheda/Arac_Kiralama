package arac_kiralama.models;

import java.time.LocalDateTime;

public class Kiralama {
    private int id;
    private Kullanici musteri;
    private Arac arac;
    private LocalDateTime baslangicTarihi;
    private LocalDateTime bitisTarihi;
    private String kiralamaTipi;
    private double depozito;
    private double kiralamaUcreti; // Kiralama ücreti eklendi

    public Kiralama(int id, Kullanici musteri, Arac arac, LocalDateTime baslangicTarihi, LocalDateTime bitisTarihi, String kiralamaTipi, double depozito, double kiralamaUcreti) {
        this.id = id;
        this.musteri = musteri;
        this.arac = arac;
        setBaslangicTarihi(baslangicTarihi);
        setBitisTarihi(bitisTarihi);
        setKiralamaTipi(kiralamaTipi);
        setDepozito(depozito);
        setKiralamaUcreti(kiralamaUcreti); // Yeni ücret alanını ekledik
    }

    // Getter ve Setter metotları
    public int getId() { return id; }
    public Kullanici getMusteri() { return musteri; }
    public Arac getArac() { return arac; }
    public LocalDateTime getBaslangicTarihi() { return baslangicTarihi; }
    public LocalDateTime getBitisTarihi() { return bitisTarihi; }
    public String getKiralamaTipi() { return kiralamaTipi; }
    public double getDepozito() { return depozito; }
    public double getKiralamaUcreti() { return kiralamaUcreti; }


    public void setBaslangicTarihi(LocalDateTime baslangicTarihi) {
        if (baslangicTarihi.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Başlangıç tarihi geçmişte olamaz!");
        }
        this.baslangicTarihi = baslangicTarihi;
    }

    public void setBitisTarihi(LocalDateTime bitisTarihi) {
        if (bitisTarihi.isBefore(baslangicTarihi)) {
            throw new IllegalArgumentException("Bitiş tarihi, başlangıç tarihinden önce olamaz!");
        }
        this.bitisTarihi = bitisTarihi;
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

    public void setDepozito(double depozito) {
        if (depozito < 0) {
            throw new IllegalArgumentException("Depozito negatif olamaz!");
        }
        this.depozito = depozito;
    }

    public void setKiralamaUcreti(double kiralamaUcreti) {
        if (kiralamaUcreti < 0) {
            throw new IllegalArgumentException("Kiralama ücreti negatif olamaz!");
        }
        this.kiralamaUcreti = kiralamaUcreti;
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
                ", Depozito=" + depozito +
                ", Kiralama Ücreti=" + kiralamaUcreti +
                " }";
    }
}

