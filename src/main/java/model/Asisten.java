package src.main.java.model;

public class Asisten {
    private int id;
    private String nama;
    private String nim;
    private String noHp;
    private String email;

    // Constructor Lengkap
    public Asisten(int id, String nama, String nim, String noHp, String email) {
        this.id = id;
        this.nama = nama;
        this.nim = nim;
        this.noHp = noHp;
        this.email = email;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
