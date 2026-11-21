
import java.time.LocalDateTime;
import java.util.ArrayList;

public class HasilSegmentasi {

    private int id_hasil;
    private int id_proses;
    private LocalDateTime tanggal_hasil;
    private String keterangan;

    private ArrayList<DetailSegmentasi> daftarDetail =
            new ArrayList<>();

    // Constructor
    public HasilSegmentasi(int id_hasil, int id_proses,
                           String keterangan) {

        this.id_hasil = id_hasil;
        this.id_proses = id_proses;
        this.keterangan = keterangan;
        this.tanggal_hasil = LocalDateTime.now();
    }

    // Menambahkan detail hasil
    public void simpanDetail(DetailSegmentasi detail) {
        daftarDetail.add(detail);
    }

    // Getter
    public int getId_hasil() {
        return id_hasil;
    }

    public int getId_proses() {
        return id_proses;
    }

    public LocalDateTime getTanggal_hasil() {
        return tanggal_hasil;
    }

    // Mengatur kembali tanggal hasil segmentasi
    public void setTanggal_hasil(LocalDateTime tanggal_hasil) {
    this.tanggal_hasil = tanggal_hasil;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public ArrayList<DetailSegmentasi> getDaftarDetail() {
        return daftarDetail;
    }

    // Menampilkan ringkasan hasil
    public void lihatHasil() {

        System.out.println("\n===== HASIL SEGMENTASI =====");
        System.out.println("ID Hasil : " + id_hasil);
        System.out.println("ID Proses: " + id_proses);
        System.out.println("Tanggal  : " + tanggal_hasil);
        System.out.println("Keterangan: " + keterangan);
        System.out.println("Jumlah data: " + daftarDetail.size());
    }

    // Menampilkan detail setiap cluster
    public void lihatDetail(ArrayList<Donatur> daftarDonatur) {

        System.out.println("\n===== DETAIL HASIL SEGMENTASI =====");

        for (DetailSegmentasi detail : daftarDetail) {

            Donatur donatur = null;

            for (Donatur d : daftarDonatur) {

                if (d.getId_donatur() == detail.getId_donatur()) {
                    donatur = d;
                    break;
                }
            }

            if (donatur != null) {

                System.out.println("------------------------------");
                System.out.println("ID Donatur: " + donatur.getId_donatur());
                System.out.println("Nama      : " + donatur.getNama_donatur());
                System.out.println("Cluster   : " + detail.getLabel_cluster());
            }
        }
    }

    // Filter hasil berdasarkan cluster
    public void filterHasil(String labelCluster,
                            ArrayList<Donatur> daftarDonatur) {

        System.out.println("\n===== FILTER " + labelCluster + " =====");

        boolean ditemukan = false;

        for (DetailSegmentasi detail : daftarDetail) {

            if (detail.getLabel_cluster().equalsIgnoreCase(labelCluster)) {

                for (Donatur d : daftarDonatur) {

                    if (d.getId_donatur() == detail.getId_donatur()) {

                        System.out.println(
                            d.getNama_donatur() + " - " + labelCluster
                        );

                        ditemukan = true;
                    }
                }
            }
        }

        if (!ditemukan) {
            System.out.println("Tidak ada donatur pada cluster tersebut.");
        }
    }

    // Mencetak laporan sederhana
    public void cetakLaporan() {

        System.out.println("\n===== LAPORAN SEGMENTASI =====");
        System.out.println("ID Hasil: " + id_hasil);
        System.out.println("Tanggal : " + tanggal_hasil);
        System.out.println("Jumlah donatur: " + daftarDetail.size());
    }
}
