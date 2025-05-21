package arac_kiralama.models;

public class Arac {
    private int id;
    private String marka;
    private String model;
    private String kategori; // "Otomobil", "Motosiklet", "Helikopter"
    private double fiyat;
    private double depozito;

    public Arac(int id, String marka, String model, String kategori, double fiyat, double depozito) {
        this.id = id;
        this.marka = marka;
        this.model = model;
        this.kategori = kategori;
        this.fiyat = fiyat;
        this.depozito = depozito;
    }

    // Getter ve Setter metotları

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public double getFiyat() {
        return fiyat;
    }

    public void setFiyat(double fiyat) {
        this.fiyat = fiyat;
    }

    public double getDepozito() {
        return depozito;
    }

    public void setDepozito(double depozito) {
        this.depozito = depozito;
    }
}
