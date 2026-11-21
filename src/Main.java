import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);

    // Tempat menyimpan data donatur sementara
    static ArrayList<Donatur> daftarDonatur = new ArrayList<>();

    // Nomor ID otomatis
    static int nomorDonatur = 1;

    static ArrayList<TransaksiDonasi> daftarTransaksi = new ArrayList<>();
    
    // Nomor Transaks
    static int nomorTransaksi = 1;

    static PengelolaData pengelolaData = new PengelolaData();

    static ProsesSegmentasi prosesSegmentasi =
        new ProsesSegmentasi();

static HasilSegmentasi hasilSegmentasi = null;

// Menyimpan seluruh riwayat hasil segmentasi
static ArrayList<HasilSegmentasi> daftarHasilSegmentasi =
        new ArrayList<>();

static int nomorProses = 1;
static int nomorHasil = 1;
static int nomorDetail = 1;

    public static void main(String[] args) {

        // Memuat data dari penyimpanan permanen
        System.out.println("Memuat data aplikasi...");

        nomorDonatur = pengelolaData.muatDonatur(daftarDonatur);

        nomorTransaksi = pengelolaData.muatTransaksi(daftarTransaksi);

        daftarHasilSegmentasi = pengelolaData.muatHasilSegmentasi();


// Mengambil hasil segmentasi terakhir
if (!daftarHasilSegmentasi.isEmpty()) {

    hasilSegmentasi = daftarHasilSegmentasi.get(
        daftarHasilSegmentasi.size() - 1
    );

    // Menyesuaikan nomor ID agar tidak terjadi duplikasi
    for (HasilSegmentasi hasil : daftarHasilSegmentasi) {

        if (hasil.getId_hasil() >= nomorHasil) {
            nomorHasil = hasil.getId_hasil() + 1;
        }

        if (hasil.getId_proses() >= nomorProses) {
            nomorProses = hasil.getId_proses() + 1;
        }

        for (DetailSegmentasi detail : hasil.getDaftarDetail()) {

            if (detail.getId_detail() >= nomorDetail) {
                nomorDetail = detail.getId_detail() + 1;
            }
        }
    }

    System.out.println("Hasil segmentasi terakhir berhasil dipulihkan.");
}

        System.out.println("Data aplikasi berhasil dimuat.");
        System.out.println("Jumlah Donatur: " + daftarDonatur.size());
        System.out.println("Jumlah Transaksi: " + daftarTransaksi.size());

        Pengguna admin = new Pengguna(
            1, "Administrator", "admin", "admin123", "Admin"
        );

        Pengguna pimpinan = new Pengguna(
            2, "Pimpinan Yayasan", "pimpinan",
            "pimpinan123", "Pimpinan"
        );

        System.out.println("==================================");
        System.out.println(" SISTEM SEGMENTASI DONATUR");
        System.out.println("            YAYASAN");
        System.out.println("==================================");

        System.out.print("Username : ");
        String username = input.nextLine();

        System.out.print("Password : ");
        String password = input.nextLine();

        Pengguna penggunaAktif = null;

        if (admin.login(username, password)) {
            penggunaAktif = admin;
        } else if (pimpinan.login(username, password)) {
            penggunaAktif = pimpinan;
        }

        if (penggunaAktif == null) {
            System.out.println("Login gagal!");
            input.close();
            return;
        }

        System.out.println("\nLogin berhasil.");
        System.out.println("Selamat datang, "
                + penggunaAktif.getNama());

        boolean berjalan = true;

        while (berjalan) {

            System.out.println("\n========== MENU UTAMA ==========");

            if (penggunaAktif.getRole().equals("Admin")) {

                System.out.println("1. Kelola Data Donatur");
                System.out.println("2. Kelola Transaksi Donasi");
                System.out.println("3. Import Data");
                System.out.println("4. Proses Segmentasi K-Means");
                System.out.println("5. Lihat Hasil Segmentasi");
                System.out.println("6. Lihat Detail Segmentasi");
                System.out.println("7. Filter Hasil Segmentasi");
                System.out.println("8. Keluar");
            } else {

    System.out.println("\n===== MENU PIMPINAN =====");
    System.out.println("1. Lihat Hasil Segmentasi");
    System.out.println("2. Lihat Laporan");
    System.out.println("3. Filter Hasil Segmentasi");
    System.out.println("4. Keluar");

}

            System.out.print("Pilih menu: ");
            String pilihan = input.nextLine();

            if (penggunaAktif.getRole().equals("Admin")) {

                switch (pilihan) {

                    case "1":
                        menuDonatur();
                        break;

                    case "2":
                        menuTransaksi();
                        break;

                    case "3":
                        importDataDonatur();
                        break;

                    case "4":
                        prosesSegmentasiDonatur();
                        break;

                    case "5":
                        lihatHasilSegmentasi();
                        break;

                    case "6":
                        lihatDetailSegmentasi();
                        break;

                    case "7":
                        menuFilterSegmentasi();
                        break;

                    case "8":
                        berjalan = false;
                        break;

                    default:
                        System.out.println(
                            "Pilihan tidak tersedia."
                        );
                }

            } else {

                switch (pilihan) {

                    case "1":
                        lihatHasilSegmentasi();
                        break;

                    case "2":
                        lihatLaporanSegmentasi();
                        break;

                    case "3":
        menuFilterSegmentasi();
        break;

    case "4":
        berjalan = false;
        break;

                    default:
                        System.out.println(
                            "Pilihan tidak tersedia."
                        );
                }
            }
        }

        penggunaAktif.logout();
        input.close();
    }

    // ==============================
    // MENU PENGELOLAAN DATA DONATUR
    // ==============================

    public static void menuDonatur() {

        boolean kembali = false;

        while (!kembali) {

            System.out.println("\n===== KELOLA DATA DONATUR =====");
            System.out.println("1. Tambah Donatur");
            System.out.println("2. Tampilkan Data Donatur");
            System.out.println("3. Ubah Data Donatur");
            System.out.println("4. Hapus Data Donatur");
            System.out.println("5. Kembali");

            System.out.print("Pilih menu: ");
            String pilihan = input.nextLine();

            switch (pilihan) {

                case "1":
                    tambahDonatur();
                    break;

                case "2":
                    tampilDonatur();
                    break;

                case "3":
                    ubahDonatur();
                    break;

                case "4":
                    hapusDonatur();
                    break;

                case "5":
                    kembali = true;
                    break;

                default:
                    System.out.println(
                        "Pilihan tidak tersedia."
                    );
            }
        }
    }

    // ==============================
    // MENAMBAH DATA DONATUR
    // ==============================

    public static void tambahDonatur() {

        System.out.println("\n===== TAMBAH DATA DONATUR =====");

        System.out.print("Nama Donatur : ");
        String nama = input.nextLine();

        System.out.print("Alamat       : ");
        String alamat = input.nextLine();

        System.out.print("No. Telepon  : ");
        String telepon = input.nextLine();

        System.out.print("Email        : ");
        String email = input.nextLine();

        Donatur donaturBaru = new Donatur(
            nomorDonatur,
            nama,
            alamat,
            telepon,
            email
        );

        daftarDonatur.add(donaturBaru);

        // Simpan data donatur ke penyimpanan permanen
        pengelolaData.simpanDonatur(daftarDonatur);

        System.out.println(
            "Data donatur berhasil ditambahkan."
        );

        System.out.println("ID Donatur: " + nomorDonatur);

        nomorDonatur++;
    }

    // ==============================
    // MENAMPILKAN DATA DONATUR
    // ==============================

    public static void tampilDonatur() {

        System.out.println("\n===== DAFTAR DATA DONATUR =====");

        if (daftarDonatur.isEmpty()) {

            System.out.println("Belum ada data donatur.");

        } else {

            for (Donatur d : daftarDonatur) {

                System.out.println("------------------------------");
                d.lihatData();
            }

            System.out.println("------------------------------");
            System.out.println(
                "Total donatur: " + daftarDonatur.size()
            );
        }
    }

    // ==============================
    // MENGUBAH DATA DONATUR
    // ==============================

    public static void ubahDonatur() {

        System.out.println("\n===== UBAH DATA DONATUR =====");

        System.out.print("Masukkan ID Donatur: ");
        int id = bacaAngka();

        Donatur ditemukan = cariDonatur(id);

        if (ditemukan == null) {

            System.out.println("Data donatur tidak ditemukan.");
            return;
        }

        System.out.print("Nama Baru      : ");
        String nama = input.nextLine();

        System.out.print("Alamat Baru    : ");
        String alamat = input.nextLine();

        System.out.print("No. Telepon Baru: ");
        String telepon = input.nextLine();

        System.out.print("Email Baru     : ");
        String email = input.nextLine();

        ditemukan.ubahData(
            nama, alamat, telepon, email
        );

        // Simpan perubahan data donatur
        pengelolaData.simpanDonatur(daftarDonatur);
    }

    // ==============================
    // MENGHAPUS DATA DONATUR
    // ==============================

    public static void hapusDonatur() {

        System.out.println("\n===== HAPUS DATA DONATUR =====");

        System.out.print("Masukkan ID Donatur: ");
        int id = bacaAngka();

        Donatur ditemukan = cariDonatur(id);

        if (ditemukan == null) {

            System.out.println("Data donatur tidak ditemukan.");

        } else {

            daftarDonatur.remove(ditemukan);

            // Simpan perubahan setelah donatur dihapus
            pengelolaData.simpanDonatur(daftarDonatur);

            System.out.println(
                "Data donatur berhasil dihapus."
            );
        }
    }

    // ==============================
    // MENCARI DONATUR BERDASARKAN ID
    // ==============================

    public static Donatur cariDonatur(int id) {

        for (Donatur d : daftarDonatur) {

            if (d.getId_donatur() == id) {
                return d;
            }
        }

        return null;
    }

    // ==============================
    // MEMBACA INPUT ANGKA
    // ==============================

    public static int bacaAngka() {

        while (true) {

            String nilai = input.nextLine();

            try {
                return Integer.parseInt(nilai);

            } catch (NumberFormatException e) {

                System.out.print(
                    "Masukkan angka yang benar: "
                );
            }
        }
    }

    // ==============================
    // MENU TRANSAKSI DONASI
    // ==============================

    public static void menuTransaksi() {

        boolean kembali = false;

        while (!kembali) {

            System.out.println("\n===== TRANSAKSI DONASI =====");
            System.out.println("1. Tambah Transaksi");
            System.out.println("2. Tampilkan Transaksi");
            System.out.println("3. Ubah Transaksi");
            System.out.println("4. Hapus Transaksi");
            System.out.println("5. Kembali");

            System.out.print("Pilih menu: ");
            String pilihan = input.nextLine();

            switch (pilihan) {

                case "1":
                    tambahTransaksi();
                    break;

                case "2":
                    tampilTransaksi();
                    break;

                case "3":
                    ubahTransaksi();
                    break;

                case "4":
                    hapusTransaksi();
                    break;

                case "5":
                    kembali = true;
                    break;

                default:
                    System.out.println("Pilihan tidak tersedia.");
            }
        }
    }

    // ==============================
    // TAMBAH TRANSAKSI
    // ==============================

    public static void tambahTransaksi() {

        if (daftarDonatur.isEmpty()) {
            System.out.println(
                "Tambahkan data donatur terlebih dahulu."
            );
            return;
        }

        System.out.println("\n===== TAMBAH TRANSAKSI =====");

        tampilDonatur();

        System.out.print("Masukkan ID Donatur: ");
        int idDonatur = bacaAngka();

        Donatur donatur = cariDonatur(idDonatur);

        if (donatur == null) {
            System.out.println("Donatur tidak ditemukan.");
            return;
        }

        LocalDate tanggal = bacaTanggal();

        System.out.print("Jumlah Donasi: Rp ");
        double jumlah = bacaJumlah();

        System.out.print("Metode Donasi (Tunai/Transfer): ");
        String metode = input.nextLine();

        TransaksiDonasi transaksiBaru =
            new TransaksiDonasi(
                nomorTransaksi,
                idDonatur,
                tanggal,
                jumlah,
                metode
            );

        daftarTransaksi.add(transaksiBaru);

        // Simpan transaksi donasi ke penyimpanan permanen
        pengelolaData.simpanTransaksi(daftarTransaksi);

        System.out.println("Transaksi berhasil ditambahkan.");
        System.out.println("ID Transaksi: " + nomorTransaksi);

        nomorTransaksi++;
    }

    // ==============================
    // TAMPIL TRANSAKSI
    // ==============================

    public static void tampilTransaksi() {

        System.out.println("\n===== DAFTAR TRANSAKSI =====");

        if (daftarTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }

        for (TransaksiDonasi t : daftarTransaksi) {

            System.out.println("---------------------------");
            t.lihatTransaksi();

            Donatur d = cariDonatur(t.getId_donatur());

            if (d != null) {
                System.out.println(
                    "Nama Donatur : " + d.getNama_donatur()
                );
            }
        }

        System.out.println("---------------------------");
        System.out.println(
            "Total transaksi: " + daftarTransaksi.size()
        );
    }

    // ==============================
    // UBAH TRANSAKSI
    // ==============================

    public static void ubahTransaksi() {

        tampilTransaksi();

        if (daftarTransaksi.isEmpty()) {
            return;
        }

        System.out.print("Masukkan ID Transaksi: ");
        int id = bacaAngka();

        TransaksiDonasi ditemukan = cariTransaksi(id);

        if (ditemukan == null) {
            System.out.println("Transaksi tidak ditemukan.");
            return;
        }

        LocalDate tanggal = bacaTanggal();

        System.out.print("Jumlah Donasi Baru: Rp ");
        double jumlah = bacaJumlah();

        System.out.print("Metode Donasi Baru: ");
        String metode = input.nextLine();

        ditemukan.ubahTransaksi(
            tanggal, jumlah, metode
        );

        // Simpan perubahan transaksi ke penyimpanan permanen
        pengelolaData.simpanTransaksi(daftarTransaksi);
    }

    // ==============================
    // HAPUS TRANSAKSI
    // ==============================

    public static void hapusTransaksi() {

        tampilTransaksi();

        if (daftarTransaksi.isEmpty()) {
            return;
        }

        System.out.print("Masukkan ID Transaksi: ");
        int id = bacaAngka();

        TransaksiDonasi ditemukan = cariTransaksi(id);

        if (ditemukan == null) {
            System.out.println("Transaksi tidak ditemukan.");
        } else {
            daftarTransaksi.remove(ditemukan);
            System.out.println("Transaksi berhasil dihapus.");

            // Simpan perubahan setelah transaksi dihapus
            pengelolaData.simpanTransaksi(daftarTransaksi);
        }
    }

    // ==============================
    // CARI TRANSAKSI
    // ==============================

    public static TransaksiDonasi cariTransaksi(int id) {

        for (TransaksiDonasi t : daftarTransaksi) {

            if (t.getId_transaksi() == id) {
                return t;
            }
        }

        return null;
    }

    // ==============================
    // VALIDASI TANGGAL
    // ==============================

    public static LocalDate bacaTanggal() {

        while (true) {

            System.out.print("Tanggal Donasi (YYYY-MM-DD): ");
            String tanggal = input.nextLine();

            try {
                return LocalDate.parse(tanggal);

            } catch (Exception e) {
                System.out.println(
                    "Format tanggal salah. Contoh: 2026-11-13"
                );
            }
        }
    }

    // ==============================
    // VALIDASI JUMLAH DONASI
    // ==============================

    public static double bacaJumlah() {

        while (true) {

            String nilai = input.nextLine();

            try {
                double jumlah = Double.parseDouble(nilai);

                if (Double.isFinite(jumlah) && jumlah > 0) {
                    return jumlah;
                }

                System.out.print(
                    "Jumlah harus lebih dari 0. Masukkan lagi: "
                );

            } catch (NumberFormatException e) {

                System.out.print(
                    "Masukkan nominal angka yang benar: Rp "
                );
            }
        }
    }

    // ==============================
    // IMPORT DATA DONATUR
    // ==============================

    public static void importDataDonatur() {

        System.out.println("\n===== IMPORT DATA DONATUR =====");

        System.out.println("Format file: CSV");
        System.out.println("Contoh: C:\\Data\\data_donatur.csv");

        System.out.print("Masukkan lokasi file CSV: ");
        String lokasiFile = input.nextLine();

        nomorDonatur = pengelolaData.imporCSV(
            lokasiFile,
            daftarDonatur,
            nomorDonatur
        );

        // Simpan data donatur hasil import ke penyimpanan permanen
        pengelolaData.simpanDonatur(daftarDonatur);

    }

    // =====================================
    // MENJALANKAN PROSES SEGMENTASI K-MEANS
    // =====================================

    public static void prosesSegmentasiDonatur() {

        System.out.println("\n===== PROSES SEGMENTASI K-MEANS =====");

        if (daftarDonatur.size() < 2) {
            System.out.println(
                "Minimal harus tersedia 2 data donatur."
            );
            return;
        }

        if (daftarTransaksi.isEmpty()) {
            System.out.println(
                "Belum ada transaksi donasi."
            );
            return;
        }

        System.out.println(
            "Jumlah data donatur: " + daftarDonatur.size()
        );

        System.out.print("Masukkan jumlah cluster (K): ");
        int k = bacaAngka();

        try {

            // Menjalankan algoritma K-Means
            int[] hasil = prosesSegmentasi.jalankanKMeans(
                daftarDonatur,
                daftarTransaksi,
                k
            );

            // Membuat objek hasil segmentasi
            HasilSegmentasi hasilBaru = new HasilSegmentasi(
                nomorHasil,
                nomorProses,
                "Hasil segmentasi menggunakan algoritma K-Means"
            );

            // Menyimpan hasil setiap donatur
            for (int i = 0; i < hasil.length; i++) {

                String label = "Cluster " + (hasil[i] + 1);

                DetailSegmentasi detail = new DetailSegmentasi(
                    nomorDetail,
                    nomorHasil,
                    daftarDonatur.get(i).getId_donatur(),
                    label
                );

                hasilBaru.simpanDetail(detail);

                nomorDetail++;
            }

            // Menyimpan hasil terbaru
            hasilSegmentasi = hasilBaru;

            // Menambahkan hasil ke daftar riwayat
            daftarHasilSegmentasi.add(hasilBaru);

            // Menyimpan seluruh riwayat segmentasi
            pengelolaData.simpanHasilSegmentasi(daftarHasilSegmentasi);

            System.out.println("\nSegmentasi berhasil dilakukan.");
            System.out.println(
                "Jumlah iterasi: " +
                prosesSegmentasi.getJumlahIterasi()
            );

            System.out.println(
                "Status proses: " +
                prosesSegmentasi.getStatusProses()
            );

            nomorHasil++;
            nomorProses++;

            // Menampilkan hasil
            lihatHasilSegmentasi();

        } catch (IllegalArgumentException e) {

            System.out.println(
                "Proses segmentasi gagal: " + e.getMessage()
            );
        }
    }

    // =====================================
    // MELIHAT RIWAYAT HASIL SEGMENTASI
    // =====================================

    public static void lihatHasilSegmentasi() {

        if (daftarHasilSegmentasi.isEmpty()) {
            System.out.println("\nBelum ada riwayat hasil segmentasi.");
            return;
        }

        System.out.println("\n===== RIWAYAT HASIL SEGMENTASI =====");

        // Menampilkan seluruh riwayat
        for (int i = 0; i < daftarHasilSegmentasi.size(); i++) {

            HasilSegmentasi hasil = daftarHasilSegmentasi.get(i);

            System.out.println("\n" + (i + 1) + ". ID Hasil: "
                    + hasil.getId_hasil());

            System.out.println("   Jumlah Data: "
                    + hasil.getDaftarDetail().size());
        }

        System.out.println("\n0. Kembali");

        System.out.print("Pilih riwayat yang ingin dilihat: ");
        int pilihan = bacaAngka();

        if (pilihan == 0) {
            return;
        }

        if (pilihan < 1 || pilihan > daftarHasilSegmentasi.size()) {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        // Mengambil hasil segmentasi yang dipilih
        HasilSegmentasi hasilDipilih =
                daftarHasilSegmentasi.get(pilihan - 1);

        // Menampilkan informasi hasil
        hasilDipilih.lihatHasil();

        System.out.println("\nJumlah anggota setiap cluster:");

        ArrayList<String> daftarLabel = new ArrayList<>();

        // Mengambil label cluster
        for (DetailSegmentasi detail :
                hasilDipilih.getDaftarDetail()) {

            String label = detail.getLabel_cluster();

            if (!daftarLabel.contains(label)) {
                daftarLabel.add(label);
            }
        }

        // Menghitung anggota setiap cluster
        for (String label : daftarLabel) {

            int jumlah = 0;

            for (DetailSegmentasi detail :
                    hasilDipilih.getDaftarDetail()) {

                if (detail.getLabel_cluster().equals(label)) {
                    jumlah++;
                }
            }

            System.out.println(label + ": " + jumlah + " donatur");
        }
    }

    // =====================================
    // MEMILIH RIWAYAT HASIL SEGMENTASI
    // =====================================

    public static HasilSegmentasi pilihRiwayatSegmentasi() {

        if (daftarHasilSegmentasi.isEmpty()) {
            System.out.println("\nBelum ada riwayat hasil segmentasi.");
            return null;
        }

        System.out.println("\n===== PILIH RIWAYAT SEGMENTASI =====");

        for (int i = 0; i < daftarHasilSegmentasi.size(); i++) {

            HasilSegmentasi hasil = daftarHasilSegmentasi.get(i);

            System.out.println(
                (i + 1) + ". ID Hasil: " + hasil.getId_hasil()
                + " | Jumlah Data: " + hasil.getDaftarDetail().size()
            );
        }

        System.out.println("0. Kembali");

        System.out.print("Pilih riwayat: ");
        int pilihan = bacaAngka();

        if (pilihan == 0) {
            return null;
        }

        if (pilihan < 1 || pilihan > daftarHasilSegmentasi.size()) {
            System.out.println("Pilihan riwayat tidak valid.");
            return null;
        }

        return daftarHasilSegmentasi.get(pilihan - 1);
    }

   
    // =====================================
    // MELIHAT DETAIL SEGMENTASI
    // =====================================

    public static void lihatDetailSegmentasi() {

        HasilSegmentasi hasilDipilih = pilihRiwayatSegmentasi();

        if (hasilDipilih == null) {
            return;
        }

        hasilDipilih.lihatHasil();
        hasilDipilih.lihatDetail(daftarDonatur);
    }
    
    // =====================================
    // MELIHAT LAPORAN SEGMENTASI
    // =====================================

    public static void lihatLaporanSegmentasi() {

        HasilSegmentasi hasilDipilih = pilihRiwayatSegmentasi();

        if (hasilDipilih == null) {
            return;
        }

        System.out.println("\n===== LAPORAN SEGMENTASI =====");

        hasilDipilih.cetakLaporan();

        hasilDipilih.lihatDetail(daftarDonatur);
    }

    
    // =====================================
    // FILTER HASIL SEGMENTASI
    // =====================================

    public static void menuFilterSegmentasi() {

        // Memilih riwayat segmentasi
        HasilSegmentasi hasilDipilih = pilihRiwayatSegmentasi();

        if (hasilDipilih == null) {
            return;
        }

        System.out.println("\n===== FILTER HASIL SEGMENTASI =====");

        ArrayList<String> daftarLabel = new ArrayList<>();

        // Mengambil label cluster dari riwayat yang dipilih
        for (DetailSegmentasi detail :
                hasilDipilih.getDaftarDetail()) {

            String label = detail.getLabel_cluster();

            if (!daftarLabel.contains(label)) {
                daftarLabel.add(label);
            }
        }

        if (daftarLabel.isEmpty()) {
            System.out.println("Data cluster tidak ditemukan.");
            return;
        }

        // Menampilkan pilihan cluster
        for (int i = 0; i < daftarLabel.size(); i++) {
            System.out.println(
                (i + 1) + ". " + daftarLabel.get(i)
            );
        }

        System.out.print("Pilih cluster: ");
        int pilihan = bacaAngka();

        if (pilihan < 1 || pilihan > daftarLabel.size()) {
            System.out.println("Pilihan cluster tidak valid.");
            return;
        }

        String label = daftarLabel.get(pilihan - 1);

        // Menampilkan hasil filter dari riwayat yang dipilih
        hasilDipilih.filterHasil(label, daftarDonatur);
    }

}