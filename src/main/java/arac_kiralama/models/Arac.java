package arac_kiralama.models;

public class Arac {
    private int id;
    private String marka;
    private String model;
    private String kategori; // Veri tabanından çekilecek
    private double kiralamaUcreti;
    private double bedel;
    private double depozito;

    //  Yapıcı metoddan kategori doğrulama kaldırıldı, doğrudan atanıyor
    public Arac(int id, String marka, String model, String kategori, double kiralamaUcreti, double bedel) {
        this.id = id;
        this.marka = marka;
        this.model = model;
        this.kiralamaUcreti = kiralamaUcreti;
        this.bedel = bedel;
        this.depozito = (bedel > 2000000) ? bedel * 0.10 : 0;

        //  Kategori doğrudan atanıyor, null veya boşsa "Otomobil" olarak belirleniyor
        this.kategori = (kategori == null || kategori.isEmpty()) ? "Otomobil" : kategori;
    }

    // Getter metotları
    public int getId() { return id; }
    public String getMarka() { return marka; }
    public String getModel() { return model; }
    public String getKategori() { return kategori; }
    public double getKiralamaUcreti() { return kiralamaUcreti; }
    public double getBedel() { return bedel; }
    public double getDepozito() { return depozito; }

    //  Kiralama Ücretini Hesaplayan Metot
    public double hesaplaKiralamaUcreti(String kiralamaTipi) {
        if (kiralamaUcreti <= 0) {
            throw new IllegalArgumentException("Araç kiralama ücreti geçersiz!");
        }

        double fiyat = switch (kiralamaTipi.toLowerCase()) {
            case "saatlik" -> kiralamaUcreti;
            case "gunluk" -> kiralamaUcreti * 8;
            case "haftalik" -> kiralamaUcreti * 8 * 7;
            case "aylik" -> kiralamaUcreti * 8 * 30;
            default -> throw new IllegalArgumentException("Geçersiz kiralama tipi!");
        };

        //  Araç kategorisine göre fiyat belirleme
        if (kategori.equalsIgnoreCase("Helikopter")) {
            fiyat *= 2.5;
        } else if (kategori.equalsIgnoreCase("Motosiklet")) {
            fiyat *= 0.75;
        }

        return fiyat;
    }

    @Override
    public String toString() {
        return "Arac{" +
                "id=" + id +
                ", marka='" + marka + '\'' +
                ", model='" + model + '\'' +
                ", kategori='" + kategori + '\'' +
                ", kiralamaUcreti=" + kiralamaUcreti +
                ", bedel=" + bedel +
                ", depozito=" + depozito +
                '}';
    }
}

