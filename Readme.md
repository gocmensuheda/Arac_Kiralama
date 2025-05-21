Araç Kiralama Sistemi
Bu proje, terminal tabanlı bir araç kiralama uygulamasıdır. Kullanıcılar sisteme giriş yaparak araçları listeleyebilir, filtreleyebilir ve kiralama işlemleri gerçekleştirebilir.

📌 Proje Hakkında
Dil: Java 21

Veritabanı: PostgreSQL

Bağımlılıklar: Maven kullanılarak PostgreSQL JDBC sürücüsü

Mimari: Katmanlı mimari (DAO, Service, Model, Main)

Özellikler:

Kullanıcı giriş sistemi (email + şifre)

Araç ekleme, listeleme ve kategori bazlı filtreleme

Kiralama işlemleri

Exception handling ve transaction yönetimi

⚙️ Kurulum Adımları
1️⃣ Projeyi Klonla
sh
git clone https://github.com/kullanici/arac-kiralama.git
cd arac-kiralama
2️⃣ Veritabanını PostgreSQL’de Oluştur
PostgreSQL'de yeni bir veritabanı oluştur:

sql
CREATE DATABASE arac_kiralama;
Mevcut tabloları ekleyin:

sql
CREATE TABLE kullanici (
id SERIAL PRIMARY KEY,
email VARCHAR(255) UNIQUE NOT NULL,
sifre VARCHAR(256) NOT NULL,
rol VARCHAR(50) NOT NULL CHECK (rol IN ('ADMIN', 'MUSTERI', 'KURUMSAL'))
);

CREATE TABLE arac (
id SERIAL PRIMARY KEY,
marka VARCHAR(100) NOT NULL,
model VARCHAR(100) NOT NULL,
kategori VARCHAR(50) NOT NULL CHECK (kategori IN ('Otomobil', 'Helikopter', 'Motosiklet')),
kiralama_ucreti DECIMAL(10,2) NOT NULL,
bedel DECIMAL(12,2) NOT NULL
);
3️⃣ Maven Bağımlılıklarını Yükle
sh
mvn clean install
4️⃣ Uygulamayı Çalıştır
sh
java -cp target/classes com.arackiralama.main.AracKiralamaApp
📜 Proje Yapısı
araç-kiralama/
├── src/main/java/com/arackiralama/
│   ├── dao/       → Veritabanı işlemleri
│   ├── model/     → Veritabanı nesneleri
│   ├── service/   → İş mantığı ve yönetim
│   ├── util/      → Yardımcı sınıflar (şifreleme, bağlantılar)
│   ├── exception/ → Hata yönetimi
│   ├── main/      → Terminal tabanlı arayüz ve uygulama başlatıcı
📌 Önemli Dosyalar
Dosya	Açıklama
AracKiralamaApp.java	Terminal menülü sistem
DatabaseUtil.java	PostgreSQL bağlantısı
KullaniciDAO.java	Kullanıcı işlemleri
AracDAO.java	Araç işlemleri
✅ Kullanım
Projeyi çalıştırdıktan sonra terminalde şu menüyü göreceksiniz:

=== Araç Kiralama Sistemi ===
1. Araçları Listele
2. Araç Ekle
3. Kategoriye Göre Araç Ara
0. Çıkış
   Araç eklemek için: "2" yazıp marka, model vb. bilgileri girin.

Araçları listelemek için: "1" yazın.

Kategoriye göre filtreleme yapmak için: "3" yazıp kategori seçin.