
import java.util.ArrayList;
import java.util.Arrays;

public class ProsesSegmentasi {

    private int id_proses;
    private int id_pengguna;
    private int jumlah_cluster;
    private String status_proses;

    private int maksimalIterasi = 100;
    private int jumlahIterasi;
    private int[] hasilCluster;

    // Constructor
    public ProsesSegmentasi() {
        this.status_proses = "Belum diproses";
    }

    // Menghitung total donasi setiap donatur
    private double[][] preprocessing(
            ArrayList<Donatur> daftarDonatur,
            ArrayList<TransaksiDonasi> daftarTransaksi) {

        double[][] data = new double[daftarDonatur.size()][2];

        for (int i = 0; i < daftarDonatur.size(); i++) {

            int idDonatur = daftarDonatur.get(i).getId_donatur();

            double totalDonasi = 0;
            int frekuensi = 0;

            for (TransaksiDonasi transaksi : daftarTransaksi) {

                if (transaksi.getId_donatur() == idDonatur) {

                    totalDonasi += transaksi.getJumlah_donasi();
                    frekuensi++;
                }
            }

            data[i][0] = totalDonasi;
            data[i][1] = frekuensi;
        }

        return data;
    }

    // Normalisasi menggunakan Z-Score
    private double[][] normalisasi(double[][] data) {

        int jumlahData = data.length;
        int jumlahFitur = 2;

        double[][] hasil = new double[jumlahData][jumlahFitur];

        for (int fitur = 0; fitur < jumlahFitur; fitur++) {

            double rataRata = 0;

            for (int i = 0; i < jumlahData; i++) {
                rataRata += data[i][fitur];
            }

            rataRata /= jumlahData;

            double jumlahKuadrat = 0;

            for (int i = 0; i < jumlahData; i++) {
                double selisih = data[i][fitur] - rataRata;
                jumlahKuadrat += selisih * selisih;
            }

            double standarDeviasi =
                    Math.sqrt(jumlahKuadrat / jumlahData);

            for (int i = 0; i < jumlahData; i++) {

                if (standarDeviasi == 0) {
                    hasil[i][fitur] = 0;
                } else {
                    hasil[i][fitur] =
                            (data[i][fitur] - rataRata)
                            / standarDeviasi;
                }
            }
        }

        return hasil;
    }

    // Menghitung jarak Euclidean
    private double hitungJarak(double[] data, double[] centroid) {

        double jumlah = 0;

        for (int i = 0; i < data.length; i++) {

            double selisih = data[i] - centroid[i];
            jumlah += selisih * selisih;
        }

        return Math.sqrt(jumlah);
    }

    // Inisialisasi centroid dengan metode farthest-first
    private double[][] inisialisasiCentroid(
            double[][] data, int k) {

        double[][] centroid = new double[k][2];

        // Centroid pertama menggunakan data pertama
        centroid[0] = data[0].clone();

        for (int c = 1; c < k; c++) {

            int indeksTerjauh = 0;
            double jarakTerbesar = -1;

            for (int i = 0; i < data.length; i++) {

                double jarakTerdekat = Double.MAX_VALUE;

                for (int j = 0; j < c; j++) {

                    double jarak = hitungJarak(
                        data[i], centroid[j]
                    );

                    if (jarak < jarakTerdekat) {
                        jarakTerdekat = jarak;
                    }
                }

                if (jarakTerdekat > jarakTerbesar) {
                    jarakTerbesar = jarakTerdekat;
                    indeksTerjauh = i;
                }
            }

            centroid[c] = data[indeksTerjauh].clone();
        }

        return centroid;
    }

    // Menentukan cluster terdekat
    private int[] tentukanCluster(
            double[][] data, double[][] centroid) {

        int[] cluster = new int[data.length];

        for (int i = 0; i < data.length; i++) {

            int clusterTerdekat = 0;
            double jarakMinimum = Double.MAX_VALUE;

            for (int j = 0; j < centroid.length; j++) {

                double jarak = hitungJarak(
                    data[i], centroid[j]
                );

                if (jarak < jarakMinimum) {
                    jarakMinimum = jarak;
                    clusterTerdekat = j;
                }
            }

            cluster[i] = clusterTerdekat;
        }

        return cluster;
    }

    // Memperbarui posisi centroid
    private double[][] updateCentroid(
            double[][] data, int[] cluster, int k,
            double[][] centroidLama) {

        double[][] centroidBaru = new double[k][2];
        int[] jumlahAnggota = new int[k];

        for (int i = 0; i < data.length; i++) {

            int nomorCluster = cluster[i];

            for (int j = 0; j < 2; j++) {
                centroidBaru[nomorCluster][j] += data[i][j];
            }

            jumlahAnggota[nomorCluster]++;
        }

        for (int i = 0; i < k; i++) {

            if (jumlahAnggota[i] > 0) {

                for (int j = 0; j < 2; j++) {
                    centroidBaru[i][j] /=
                            jumlahAnggota[i];
                }

            } else {

                // Pertahankan centroid lama jika cluster kosong
                centroidBaru[i] = centroidLama[i].clone();
            }
        }

        return centroidBaru;
    }

    // Memeriksa apakah hasil cluster sudah stabil
    private boolean clusterSama(int[] lama, int[] baru) {

        return Arrays.equals(lama, baru);
    }

    // Menjalankan algoritma K-Means
    public int[] jalankanKMeans(
            ArrayList<Donatur> daftarDonatur,
            ArrayList<TransaksiDonasi> daftarTransaksi,
            int k) {

        int jumlahData = daftarDonatur.size();

        if (jumlahData < 2) {
            throw new IllegalArgumentException(
                "Minimal harus ada 2 data donatur."
            );
        }

        if (k < 2 || k > jumlahData) {
            throw new IllegalArgumentException(
                "Nilai K harus antara 2 sampai jumlah donatur."
            );
        }

        if (daftarTransaksi.isEmpty()) {
            throw new IllegalArgumentException(
                "Belum ada transaksi donasi."
            );
        }

        this.jumlah_cluster = k;
        this.status_proses = "Sedang diproses";

        // 1. Preprocessing
        double[][] data = preprocessing(
            daftarDonatur, daftarTransaksi
        );

        // 2. Normalisasi
        double[][] dataNormal = normalisasi(data);

        // 3. Inisialisasi centroid
        double[][] centroid =
                inisialisasiCentroid(dataNormal, k);

        int[] clusterLama = new int[jumlahData];
        Arrays.fill(clusterLama, -1);

        // 4. Iterasi K-Means
        for (int iterasi = 1;
             iterasi <= maksimalIterasi; iterasi++) {

            int[] clusterBaru =
                    tentukanCluster(dataNormal, centroid);

            double[][] centroidBaru =
                    updateCentroid(
                        dataNormal, clusterBaru, k, centroid
                    );

            this.jumlahIterasi = iterasi;

            boolean stabil =
                    clusterSama(clusterLama, clusterBaru);

            clusterLama = clusterBaru.clone();
            centroid = centroidBaru;

            if (stabil) {
                break;
            }
        }

        this.hasilCluster = clusterLama;
        this.status_proses = "Selesai";

        return hasilCluster.clone();
    }

    // Getter hasil segmentasi
    public int[] getHasilCluster() {
        if (hasilCluster == null) {
            return new int[0];
        }

        return hasilCluster.clone();
    }

    public int getJumlahCluster() {
        return jumlah_cluster;
    }

    public int getJumlahIterasi() {
        return jumlahIterasi;
    }

    public String getStatusProses() {
        return status_proses;
    }
}
