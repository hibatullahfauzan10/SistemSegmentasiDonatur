
import java.time.LocalDate;

public class TransaksiDonasi {

    private int id_transaksi;
    private int id_donatur;
    private LocalDate tanggal_donasi;
    private double jumlah_donasi;
    private String metode_donasi;

    // Constructor
    public TransaksiDonasi(int id_transaksi, int id_donatur,
                           LocalDate tanggal_donasi,
                           double jumlah_donasi,
                           String metode_donasi) {

        this.id_transaksi = id_transaksi;
        this.id_donatur = id_donatur;
        this.tanggal_donasi = tanggal_donasi;
        this.jumlah_donasi = jumlah_donasi;
        this.metode_donasi = metode_donasi;
    }

    // Getter
    public int getId_transaksi() {
        return id_transaksi;
    }

    public int getId_donatur() {
        return id_donatur;
    }

    public LocalDate getTanggal_donasi() {
        return tanggal_donasi;
    }

    public double getJumlah_donasi() {
        return jumlah_donasi;
    }

    public String getMetode_donasi() {
        return metode_donasi;
    }

    // Mengubah transaksi
    public void ubahTransaksi(LocalDate tanggal,
                              double jumlah,
                              String metode) {

        this.tanggal_donasi = tanggal;
        this.jumlah_donasi = jumlah;
        this.metode_donasi = metode;

        System.out.println("Transaksi berhasil diubah.");
    }

    // Menampilkan transaksi
    public void lihatTransaksi() {

        System.out.println("ID Transaksi : " + id_transaksi);
        System.out.println("ID Donatur   : " + id_donatur);
        System.out.println("Tanggal      : " + tanggal_donasi);
        System.out.println("Jumlah Donasi: Rp "
                + String.format("%,.0f", jumlah_donasi));
        System.out.println("Metode       : " + metode_donasi);
    }
}
