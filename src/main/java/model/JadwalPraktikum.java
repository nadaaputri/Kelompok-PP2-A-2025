package src.main.java.model;

import java.sql.Time;

public class JadwalPraktikum {
    private int id;
    private Praktikum praktikum;
    private Asisten asisten;
    private String hari;
    private Time jamMulai;
    private Time jamSelesai;
    private String ruang;

    public JadwalPraktikum(int id, Praktikum praktikum, Asisten asisten,
                           String hari, Time jamMulai, Time jamSelesai, String ruang) {
        this.id = id;
        this.praktikum = praktikum;
        this.asisten = asisten;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.ruang = ruang;
    }

    public int getId() { return id; }
    public Praktikum getPraktikum() { return praktikum; }
    public Asisten getAsisten() { return asisten; }
    public String getHari() { return hari; }
    public Time getJamMulai() { return jamMulai; }
    public Time getJamSelesai() { return jamSelesai; }
    public String getRuang() { return ruang; }
}
