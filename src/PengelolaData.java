import java.time.LocalDate;
import java.time.LocalDateTime;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.ArrayList;

public class PengelolaData {

    // Validasi data donatur
    public boolean validasiData(String nama, String alamat,
                                String telepon, String email) {

        if (nama == null || nama.trim().isEmpty()) {
            return false;
        }

        if (alamat == null || alamat.trim().isEmpty()) {
            return false;
        }

        if (telepon == null || !telepon.matches("[0-9+\\- ]{8,20}")) {
            return false;
        }

        if (email == null ||
                !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return false;
        }

        return true;
    }

    // Import data donatur dari CSV
    public int imporCSV(String lokasiFile,
                        ArrayList<Donatur> daftarDonatur,
                        int idAwal) {

        int jumlahBerhasil = 0;
        int nomorBaris = 0;
        int idBaru = idAwal;

        try {

            Path file = Path.of(lokasiFile);

            if (!Files.exists(file)) {
                System.out.println("File CSV tidak ditemukan.");
                return idAwal;
            }

            for (String baris : Files.readAllLines(
                    file, StandardCharsets.UTF_8)) {

                nomorBaris++;

                if (baris.trim().isEmpty()) {
                    continue;
                }

                // Lewati baris judul/header
                if (nomorBaris == 1 &&
                        baris.toLowerCase().startsWith("nama;")) {
                    continue;
                }

                String[] data = baris.split(";", -1);

                if (data.length != 4) {
                    System.out.println(
                        "Baris " + nomorBaris +
                        " dilewati: jumlah kolom tidak sesuai."
                    );
                    continue;
                }

                String nama = data[0].trim();
                String alamat = data[1].trim();
                String telepon = data[2].trim();
                String email = data[3].trim();

                if (!validasiData(nama, alamat, telepon, email)) {
                    System.out.println(
                        "Baris " + nomorBaris +
                        " dilewati: data tidak valid."
                    );
                    continue;
                }

                Donatur donaturBaru = new Donatur(
                    idBaru,
                    nama,
                    alamat,
                    telepon,
                    email
                );

                daftarDonatur.add(donaturBaru);

                idBaru++;
                jumlahBerhasil++;
            }

            System.out.println("\nImport CSV selesai.");
            System.out.println(
                "Jumlah data berhasil diimpor: " + jumlahBerhasil
            );

            return idBaru;

        } catch (IOException e) {

            System.out.println(
                "Gagal membaca file: " + e.getMessage()
            );

            return idAwal;
        }
    }

    // Method input manual disediakan untuk pengembangan
    public void inputManual() {
        System.out.println(
            "Input manual dilakukan melalui menu Data Donatur."
        );
    }

    // Import Excel akan dikembangkan menggunakan library tambahan
    public void imporExcel() {
        System.out.println(
            "Fitur import Excel belum tersedia."
        );
    }

    // =====================================
    // PENYIMPANAN DATA DONATUR
    // =====================================

    public void simpanDonatur(ArrayList<Donatur> daftarDonatur) {

        try {

            Path folder = Path.of("data");
            Files.createDirectories(folder);

            Path file = folder.resolve("donatur.csv");

            StringBuilder isi = new StringBuilder();

            isi.append("id;nama;alamat;telepon;email\n");

            for (Donatur d : daftarDonatur) {

                isi.append(d.getId_donatur()).append(";")
                   .append(d.getNama_donatur()).append(";")
                   .append(d.getAlamat()).append(";")
                   .append(d.getNo_telepon()).append(";")
                   .append(d.getEmail()).append("\n");
            }

            Files.writeString(
                file,
                isi.toString(),
                StandardCharsets.UTF_8
            );

            System.out.println("Data donatur berhasil disimpan.");

        } catch (IOException e) {

            System.out.println(
                "Gagal menyimpan donatur: " + e.getMessage()
            );
        }
    }

    // =====================================
    // MEMUAT DATA DONATUR
    // =====================================

    public int muatDonatur(ArrayList<Donatur> daftarDonatur) {

        Path file = Path.of("data", "donatur.csv");

        if (!Files.exists(file)) {
            return 1;
        }

        int idTerbesar = 0;

        try {

            daftarDonatur.clear();

            for (String baris : Files.readAllLines(
                    file, StandardCharsets.UTF_8)) {

                if (baris.trim().isEmpty() ||
                        baris.toLowerCase().startsWith("id;")) {
                    continue;
                }

                String[] data = baris.split(";", -1);

                if (data.length != 5) {
                    continue;
                }

                int id = Integer.parseInt(data[0]);

                Donatur d = new Donatur(
                    id,
                    data[1],
                    data[2],
                    data[3],
                    data[4]
                );

                daftarDonatur.add(d);

                if (id > idTerbesar) {
                    idTerbesar = id;
                }
            }

            System.out.println(
                "Data donatur berhasil dimuat: "
                + daftarDonatur.size()
            );

        } catch (Exception e) {

            System.out.println(
                "Gagal memuat donatur: " + e.getMessage()
            );
        }

        return idTerbesar + 1;
    }

    // =====================================
    // PENYIMPANAN TRANSAKSI DONASI
    // =====================================

    public void simpanTransaksi(
            ArrayList<TransaksiDonasi> daftarTransaksi) {

        try {

            Path folder = Path.of("data");
            Files.createDirectories(folder);

            Path file = folder.resolve("transaksi.csv");

            StringBuilder isi = new StringBuilder();

            isi.append(
                "id_transaksi;id_donatur;tanggal;jumlah;metode\n"
            );

            for (TransaksiDonasi t : daftarTransaksi) {

                isi.append(t.getId_transaksi()).append(";")
                   .append(t.getId_donatur()).append(";")
                   .append(t.getTanggal_donasi()).append(";")
                   .append(t.getJumlah_donasi()).append(";")
                   .append(t.getMetode_donasi()).append("\n");
            }

            Files.writeString(
                file,
                isi.toString(),
                StandardCharsets.UTF_8
            );

            System.out.println("Transaksi berhasil disimpan.");

        } catch (IOException e) {

            System.out.println(
                "Gagal menyimpan transaksi: " + e.getMessage()
            );
        }
    }

    // =====================================
    // MEMUAT TRANSAKSI DONASI
    // =====================================

    public int muatTransaksi(
            ArrayList<TransaksiDonasi> daftarTransaksi) {

        Path file = Path.of("data", "transaksi.csv");

        if (!Files.exists(file)) {
            return 1;
        }

        int idTerbesar = 0;

        try {

            daftarTransaksi.clear();

            for (String baris : Files.readAllLines(
                    file, StandardCharsets.UTF_8)) {

                if (baris.trim().isEmpty() ||
                        baris.toLowerCase().startsWith("id_transaksi;")) {
                    continue;
                }

                String[] data = baris.split(";", -1);

                if (data.length != 5) {
                    continue;
                }

                int idTransaksi = Integer.parseInt(data[0]);
                int idDonatur = Integer.parseInt(data[1]);

                LocalDate tanggal = LocalDate.parse(data[2]);

                double jumlah = Double.parseDouble(data[3]);

                String metode = data[4];

                TransaksiDonasi transaksi =
                    new TransaksiDonasi(
                        idTransaksi,
                        idDonatur,
                        tanggal,
                        jumlah,
                        metode
                    );

                daftarTransaksi.add(transaksi);

                if (idTransaksi > idTerbesar) {
                    idTerbesar = idTransaksi;
                }
            }

            System.out.println(
                "Transaksi berhasil dimuat: "
                + daftarTransaksi.size()
            );

        } catch (Exception e) {

            System.out.println(
                "Gagal memuat transaksi: " + e.getMessage()
            );
        }

        return idTerbesar + 1;
    }


// =====================================
// SIMPAN RIWAYAT HASIL SEGMENTASI
// =====================================

public void simpanHasilSegmentasi(
        ArrayList<HasilSegmentasi> daftarHasil) {

    Path folder = Path.of("data");

    Path fileHasil = folder.resolve("hasil_segmentasi.csv");
    Path fileDetail = folder.resolve("detail_segmentasi.csv");

    ArrayList<String> barisHasil = new ArrayList<>();
    ArrayList<String> barisDetail = new ArrayList<>();

    barisHasil.add(
        "id_hasil;id_proses;tanggal_hasil;keterangan"
    );

    barisDetail.add(
        "id_detail;id_hasil;id_donatur;label_cluster"
    );

    for (HasilSegmentasi hasil : daftarHasil) {

        barisHasil.add(
            hasil.getId_hasil() + ";" +
            hasil.getId_proses() + ";" +
            hasil.getTanggal_hasil() + ";" +
            hasil.getKeterangan()
        );

        for (DetailSegmentasi detail :
                hasil.getDaftarDetail()) {

            barisDetail.add(
                detail.getId_detail() + ";" +
                detail.getId_hasil() + ";" +
                detail.getId_donatur() + ";" +
                detail.getLabel_cluster()
            );
        }

    }

    try {

        Files.createDirectories(folder);

        Files.write(
            fileHasil,
            barisHasil,
            StandardCharsets.UTF_8
        );

        Files.write(
            fileDetail,
            barisDetail,
            StandardCharsets.UTF_8
        );

        System.out.println(
            "Riwayat segmentasi berhasil disimpan."
        );

} catch (IOException e) {

    System.out.println(
        "Gagal menyimpan riwayat segmentasi: "
        + e.getMessage()
    );
        }
    }

    // =====================================
    // MEMUAT RIWAYAT HASIL SEGMENTASI
    // =====================================

    public ArrayList<HasilSegmentasi> muatHasilSegmentasi() {

        ArrayList<HasilSegmentasi> daftarHasil =
                new ArrayList<>();

        Path fileHasil = Path.of("data", "hasil_segmentasi.csv");
        Path fileDetail = Path.of("data", "detail_segmentasi.csv");

        try {

            // Membaca data utama hasil segmentasi
            if (Files.exists(fileHasil)) {

                for (String baris : Files.readAllLines(
                        fileHasil, StandardCharsets.UTF_8)) {

                    if (baris.trim().isEmpty() ||
                            baris.startsWith("id_hasil;")) {
                        continue;
                    }

                    String[] data = baris.split(";", -1);

                    if (data.length != 4) {
                        continue;
                    }

                    int idHasil = Integer.parseInt(data[0]);
                    int idProses = Integer.parseInt(data[1]);

                    HasilSegmentasi hasil = new HasilSegmentasi(
                        idHasil,
                        idProses,
                        data[3]
                    );

                    hasil.setTanggal_hasil(
                        LocalDateTime.parse(data[2])
                    );

                    daftarHasil.add(hasil);
                }
            }

            // Membaca detail setiap hasil segmentasi
            if (Files.exists(fileDetail)) {

                for (String baris : Files.readAllLines(
                        fileDetail, StandardCharsets.UTF_8)) {

                    if (baris.trim().isEmpty() ||
                            baris.startsWith("id_detail;")) {
                        continue;
                    }

                    String[] data = baris.split(";", -1);

                    if (data.length != 4) {
                        continue;
                    }

                    int idDetail = Integer.parseInt(data[0]);
                    int idHasil = Integer.parseInt(data[1]);
                    int idDonatur = Integer.parseInt(data[2]);

                    DetailSegmentasi detail = new DetailSegmentasi(
                        idDetail,
                        idHasil,
                        idDonatur,
                        data[3]
                    );

                    // Memasukkan detail ke hasil yang sesuai
                    for (HasilSegmentasi hasil : daftarHasil) {

                        if (hasil.getId_hasil() == idHasil) {
                            hasil.simpanDetail(detail);
                            break;
                        }
                    }
                }
            }

            System.out.println(
                "Riwayat segmentasi berhasil dimuat: "
                + daftarHasil.size()
            );

        } catch (Exception e) {

            System.out.println(
                "Gagal memuat riwayat segmentasi: "
                + e.getMessage()
            );
        }

        return daftarHasil;
    }
}