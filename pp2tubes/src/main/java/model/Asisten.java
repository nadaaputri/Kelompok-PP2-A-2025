package src.main.java.model;

public class Asisten {
    private String nim;
    private String nama;
    private String email;
    private String noHP;

    // Constructor Kosong
    public Asisten() {}

    // Constructor dengan Parameter
    public Asisten(String nim, String nama, String email, String noHP) {
        this.nim = nim;
        this.nama = nama;
        this.email = email;
        this.noHP = noHP;
    }

    // Getter dan Setter
    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNoHP() { return noHP; }
    public void setNoHP(String noHP) { this.noHP = noHP; }
}