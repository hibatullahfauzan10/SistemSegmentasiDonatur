public class Pengguna {


private int id_pengguna;
private String nama;
private String username;
private String password;
private String role;

// Constructor
public Pengguna(int id_pengguna, String nama,
String username, String password, String role) {
this.id_pengguna = id_pengguna;
this.nama = nama;
this.username = username;
this.password = password;
this.role = role;
}

// Method login
public boolean login(String usernameInput, String passwordInput) {
return username.equals(usernameInput)
&& password.equals(passwordInput);
}

// Method logout
public void logout() {
System.out.println("Pengguna berhasil logout.");
}

public void lihatHasilSegmentasi() {
System.out.println("Menampilkan hasil segmentasi...");
}

public void lihatLaporan() {
System.out.println("Menampilkan laporan...");
}

// Getter
public int getId_pengguna() {
return id_pengguna;
}

public String getNama() {
return nama;
}

public String getUsername() {
return username;
}

public String getRole() {
return role;
}
}