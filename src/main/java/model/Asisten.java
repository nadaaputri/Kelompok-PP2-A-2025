package src.main.java.model;

public class Asisten {
    private int id_asisten;
    private String nama_asisten;
    private String nim;
    private String no_hp;
    private String email;

    public Asisten(String nama_asisten, String nim, String no_hp, String email) {
        this.nama_asisten = nama_asisten;
        this.nim = nim;
        this.no_hp = no_hp;
        this.email = email;
    }

    public Asisten(int id_asisten, String nama_asisten, String nim, String no_hp, String email) {
        this.id_asisten = id_asisten;
        this.nama_asisten = nama_asisten;
        this.nim = nim;
        this.no_hp = no_hp;
        this.email = email;
    }

    public int getId_asisten() {
        return id_asisten;
    }

    public void setId_asisten(int id_asisten) {
        this.id_asisten = id_asisten;
    }

    public String getNama_asisten() {
        return nama_asisten;
    }

    public void setNama_asisten(String nama_asisten) {
        this.nama_asisten = nama_asisten;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
