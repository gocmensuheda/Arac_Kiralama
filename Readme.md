
# 🚀 Araç Kiralama Sistemi

Bu proje, **Java ve PostgreSQL** kullanarak **şifreli ve güvenli bir araç kiralama kullanıcı yönetimi** sunmaktadır. Kullanıcıların şifreleri **SHA-256 hashleme yöntemiyle güvenli bir şekilde saklanır** ve giriş doğrulama işlemi güvenli şekilde gerçekleştirilir.

## 📌 Özellikler
- 🔑 **SHA-256 ile güvenli şifreleme**
- 🔍 **Kullanıcı giriş doğrulama**
- 🏎 **Admin ve müşteri yönetimi**
- 📊 **PostgreSQL veritabanı desteği**

---

## ⚙️ Kurulum

### 1️⃣ **Projeyi Klonla**
```bash
git clone https://github.com/kullanici/arac-kiralama.git
2️⃣ PostgreSQL Bağlantısını Yapılandır
Veritabanı bağlantı bilgilerini DatabaseUtil.java dosyasında güncelle:

java
private static final String URL = "jdbc:postgresql://localhost:5432/arac_kiralama";
private static final String USER = "postgres";
private static final String PASSWORD = "şifreniz";
3️⃣ Veritabanını Hazırla
💡 SQL komutlarını çalıştırarak gerekli tabloları oluştur:

sql
CREATE TABLE kullanicilar (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    sifre TEXT NOT NULL,
    rol VARCHAR(10) NOT NULL CHECK (rol IN ('ADMIN', 'MUSTERI')),
    yas INT NOT NULL
);
📌 Eğer yaş sütunu eksikse, aşağıdaki komutu çalıştır:

sql
ALTER TABLE kullanicilar ADD COLUMN yas INT NOT NULL DEFAULT 25;
4️⃣ Java Bağımlılıklarını Yükle
💡 Maven veya Gradle kullanıyorsan, PostgreSQL JDBC bağımlılığını ekleyerek yükle:

xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.5.0</version>
</dependency>
5️⃣ Uygulamayı Çalıştır
💡 Ana sınıf olan Main.java dosyasını çalıştırarak uygulamayı başlat:

bash
javac -d bin src/arac_kiralama/main/Main.java
java -cp bin arac_kiralama.main.Main
🚀 Kullanım
➡️ Yeni Kullanıcı Kaydetme
java
KullaniciDAO dao = new KullaniciDAO();
dao.kullaniciEkle("user@example.com", "gizliSifre", "MUSTERI", 28);
➡️ Giriş Yapma
java
KullaniciDAO dao = new KullaniciDAO();
Kullanici user = dao.girisYap("user@example.com", "gizliSifre");
if (user != null) {
    System.out.println("Giriş başarılı!");
} else {
    System.out.println("Hatalı şifre!");
}
➡️ Admin Kullanıcı Güncelleme
Şifrenin SHA-256 hashlenmiş halini al ve veritabanına kaydet:

sql
UPDATE kullanicilar SET sifre = 'YENI_HASH_DEGER' WHERE email = 'admin@example.com';
🚀 Hashlenmiş şifreyi almak için:

java
System.out.println(SifrelemeUtil.sifreleSHA256("admin123"));
🛠️ Geliştirici Bilgileri
📌 Java 17+ önerilir

🏗 PostgreSQL 13+ uyumlu

🎯 SHA-256 şifreleme destekleniyor