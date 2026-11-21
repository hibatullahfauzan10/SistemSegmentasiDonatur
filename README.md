
# Sistem Segmentasi Donatur

## Deskripsi
Sistem Segmentasi Donatur merupakan aplikasi berbasis Java yang digunakan untuk mengelola data donatur dan transaksi donasi serta melakukan segmentasi donatur menggunakan algoritma K-Means.

Aplikasi ini dikembangkan untuk membantu yayasan dalam mengelompokkan donatur berdasarkan data transaksi donasi.

## Fitur Aplikasi

### Admin
- Login dan logout
- Mengelola data donatur (CRUD)
- Mengelola transaksi donasi (CRUD)
- Import data donatur melalui CSV
- Melakukan proses segmentasi K-Means
- Memilih jumlah cluster (K)
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

## Anggota Kelompok

| No. | Nama | NPM | Username GitHub |
|---|---|---|---|
| 1 | Fauzan Hibatullah | 202343501992 | [hibatullahfauzan10](https://github.com/hibatullahfauzan10) |
| 2 | Alan Juliansyah | 202343502139 | [alanjsh14](https://github.com/alanjsh14) |
| 3 | Fani Fachturrizqon | 202343501961 | [fanii11233](https://github.com/fanii11233) |
| 4 | Abdul Labib Sulaeman | 202343502063 | [sulaemanabdullabib-sketch](https://github.com/sulaemanabdullabib-sketch) |

## Pembagian Tugas Kelompok

- **Fauzan Hibatullah:** Pengembangan dan integrasi aplikasi.
- **Alan Juliansyah:** Pengelolaan dan pengujian data donatur.
- **Fani Fachturrizqon:** Pengujian proses algoritma K-Means.
- **Abdul Labib Sulaeman:** Dokumentasi dan pengujian aplikasi.

## Struktur Folder

```text
SistemSegmentasiDonatur/
├── data/
│   ├── detail_segmentasi.csv
│   ├── donatur.csv
│   ├── hasil_segmentasi.csv
│   └── transaksi.csv
├── src/
│   ├── DetailSegmentasi.java
│   ├── Donatur.java
│   ├── HasilSegmentasi.java
│   ├── Main.java
│   ├── PengelolaData.java
│   ├── Pengguna.java
│   ├── ProsesSegmentasi.java
│   └── TransaksiDonasi.java
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

**Admin**
- Username: admin
- Password: admin123

**Pimpinan**
- Username: pimpinan
- Password: pimpinan123
