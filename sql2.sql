SELECT k.email, a.marka, a.model, kiralama.kiralama_tipi, kiralama.baslangic_tarihi, kiralama.bitis_tarihi 
FROM kiralama 
JOIN kullanici k ON k.id = kiralama.kullanici_id
JOIN arac a ON a.id = kiralama.arac_id
WHERE k.email = 'musteri@email.com';

-- 🚗 Sadece kiralanmış araçları listeleme
SELECT a.marka, a.model, k.email, kiralama.kiralama_tipi, kiralama.baslangic_tarihi
FROM kiralama
JOIN arac a ON kiralama.arac_id = a.id
JOIN kullanici k ON kiralama.kullanici_id = k.id;

-- 🚀 30 yaş altı kullanıcıların kiralayamayacağı araçları getirme
SELECT a.marka, a.model, a.bedel
FROM arac a
WHERE a.bedel > 2000000;

-- 📂 Kategoriye göre araç listeleme
SELECT * FROM arac WHERE kategori = 'Otomobil';

-- 🔍 Kiralanmış araçları kategoriye göre filtreleme
SELECT a.marka, a.model, k.email, kiralama.baslangic_tarihi
FROM kiralama
JOIN arac a ON kiralama.arac_id = a.id
JOIN kullanici k ON kiralama.kullanici_id = k.id
WHERE a.kategori = 'Otomobil';