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

CREATE TABLE kiralama (
    id SERIAL PRIMARY KEY,
    kullanici_id INT REFERENCES kullanici(id),
    arac_id INT REFERENCES arac(id),
    kiralama_tipi VARCHAR(50) NOT NULL CHECK (kiralama_tipi IN ('Saatlik', 'Gunluk', 'Haftalik', 'Aylik')),
    baslangic_tarihi TIMESTAMP NOT NULL,
    bitis_tarihi TIMESTAMP NOT NULL,
    depozito DECIMAL(10,2) DEFAULT 0,
    CONSTRAINT check_kiralama_depozito CHECK (depozito >= 0)
);
