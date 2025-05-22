package arac_kiralama.models;


public class Kullanici {
    private String email;
    private String sifre;
    private String rol;
    private int yas;  // Yaş alanı eklendi

    public Kullanici(String email, String sifre, String rol) {
        this.email = email;
        this.sifre = sifre;
        this.rol = rol;
        this.yas = yas;
    }

    public String getEmail() { return email; }
    public String getSifre() { return sifre; }
    public String getRol() { return rol; }
    public int getYas() { return yas; }
}
