package src.main.java.model;

public class Praktikum {
    private int id;
    private String nama;
    private String kode;
    private int semester;
    private int sks;

    public Praktikum(int id, String nama, String kode, int semester, int sks) {
        this.id = id;
        this.nama = nama;
        this.kode = kode;
        this.semester = semester;
        this.sks = sks;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
    public String getKode() { return kode; }
    public int getSemester() { return semester; }
    public int getSks() { return sks; }
}