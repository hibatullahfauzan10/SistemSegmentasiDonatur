
public class Donatur {

    private int id_donatur;
    private String nama_donatur;
    private String alamat;
    private String no_telepon;
    private String email;

    // Constructor
    public Donatur(int id_donatur, String nama_donatur,
                   String alamat, String no_telepon, String email) {

        this.id_donatur = id_donatur;
        this.nama_donatur = nama_donatur;
        this.alamat = alamat;
        this.no_telepon = no_telepon;
        this.email = email;
    }

    // Getter
    public int getId_donatur() {
        return id_donatur;
    }

    public String getNama_donatur() {
        return nama_donatur;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNo_telepon() {
        return no_telepon;
    }

    public String getEmail() {
        return email;
    }

    // Setter
    public void setNama_donatur(String nama_donatur) {
        this.nama_donatur = nama_donatur;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNo_telepon(String no_telepon) {
        this.no_telepon = no_telepon;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Menampilkan data donatur
    public void lihatData() {
        System.out.println("ID Donatur : " + id_donatur);
        System.out.println("Nama       : " + nama_donatur);
        System.out.println("Alamat     : " + alamat);
        System.out.println("No. Telepon: " + no_telepon);
        System.out.println("Email      : " + email);
    }

    // Mengubah data donatur
    public void ubahData(String nama, String alamat,
                         String telepon, String email) {

        this.nama_donatur = nama;
        this.alamat = alamat;
        this.no_telepon = telepon;
        this.email = email;

        System.out.println("Data donatur berhasil diubah.");
    }
}
