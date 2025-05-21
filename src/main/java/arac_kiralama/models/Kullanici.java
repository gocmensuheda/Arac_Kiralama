package arac_kiralama.models;


public class Kullanici {
    private String email;
    private String sifre;
    private String rol; // "ADMIN" veya "MUSTERİ"

    public Kullanici(String email, String sifre, String rol) {
        this.email = sifre;
        this.sifre = sifre;
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
// Getter ve Setter metotları
}