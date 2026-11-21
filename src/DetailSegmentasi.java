public class DetailSegmentasi {

    private int id_detail;
    private int id_hasil;
    private int id_donatur;
    private String label_cluster;

    // Constructor
    public DetailSegmentasi(int id_detail, int id_hasil,
                            int id_donatur, String label_cluster) {

        this.id_detail = id_detail;
        this.id_hasil = id_hasil;
        this.id_donatur = id_donatur;
        this.label_cluster = label_cluster;
    }

    // Getter
    public int getId_detail() {
        return id_detail;
    }

    public int getId_hasil() {
        return id_hasil;
    }

    public int getId_donatur() {
        return id_donatur;
    }

    public String getLabel_cluster() {
        return label_cluster;
    }

    // Menampilkan detail segmentasi
    public void lihatDetail() {

        System.out.println("ID Detail    : " + id_detail);
        System.out.println("ID Hasil     : " + id_hasil);
        System.out.println("ID Donatur   : " + id_donatur);
        System.out.println("Label Cluster: " + label_cluster);
    }
}
