package arac_kiralama.models;

public class Kullanici {
    private int id;
    private String email;
    private String sifre;
    private String rol;
    private int yas;
    private double depozito;

    // 🛠 TAM PARAMETRELİ CONSTRUCTOR
    public Kullanici(int id, String email, String sifre, String rol, int yas, double depozito) {
        this.id = id;
        this.email = email;
        this.sifre = sifre;
        this.rol = rol;
        this.yas = yas;
        this.depozito = depozito;
    }

    // 🛠 EKSİK PARAMETRELİ CONSTRUCTOR (Varsayılan Değerlerle)
    public Kullanici(int id, String email, String rol) {
        this.id = id;
        this.email = email;
        this.rol = rol;
        this.yas = 0;      // Varsayılan yaş değeri
        this.depozito = 0; // Varsayılan depozito değeri
    }

    // Getter ve Setter metotları
    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getSifre() { return sifre; }
    public String getRol() { return rol; }
    public int getYas() { return yas; }
    public double getDepozito() { return depozito; }

    public boolean isKurumsal() {
        return email.endsWith("@kurumsal.com"); // Örneğin, "@kurumsal.com" uzantılı e-posta adresleri kurumsal kabul edilir
    }


    public void setYas(int yas) {
        if (yas < 0) {
            throw new IllegalArgumentException("Yaş negatif olamaz!");
        }
        this.yas = yas;
    }

    public void setDepozito(double depozito) {
        if (depozito < 0) {
            throw new IllegalArgumentException("Depozito negatif olamaz!");
        }
        this.depozito = depozito;
    }

    @Override
    public String toString() {
        return "Kullanici{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                ", yas=" + yas +
                ", depozito=" + depozito +
                '}';
    }
}
