
# Sistem Segmentasi Donatur

## Deskripsi
Sistem Segmentasi Donatur merupakan aplikasi berbasis Java yang digunakan untuk mengelola data donatur dan transaksi donasi serta melakukan segmentasi donatur menggunakan algoritma K-Means.

Aplikasi ini dikembangkan untuk membantu Yayasan dalam mengelompokkan donatur berdasarkan data transaksi donasi.

## Fitur Aplikasi

### Admin
- Login dan logout
- Mengelola data donatur (CRUD)
- Mengelola transaksi donasi (CRUD)
- Import data donatur melalui CSV
- Melakukan proses segmentasi K-Means
- Melihat hasil dan riwayat segmentasi
- Melihat detail segmentasi
- Memfilter hasil berdasarkan cluster

### Pimpinan Yayasan
- Login dan logout
- Melihat hasil segmentasi
- Melihat laporan segmentasi
- Melihat detail segmentasi
- Memfilter hasil berdasarkan cluster dan riwayat

## Teknologi
- Java
- Visual Studio Code
- Git dan GitHub
- Algoritma K-Means
- Penyimpanan data CSV

## Struktur Folder
```text
SistemSegmentasiDonatur/
├── data/
├── src/
├── .gitignore
└── README.md
```

## Cara Menjalankan Aplikasi

1. Pastikan Java JDK sudah terinstal.
2. Buka terminal pada folder proyek.
3. Kompilasi program:

   javac -d out src/*.java

4. Jalankan aplikasi:

   java -cp out Main

## Akun Demo

Admin:
- Username: admin
- Password: admin123

Pimpinan:
- Username: pimpinan
- Password: pimpinan123