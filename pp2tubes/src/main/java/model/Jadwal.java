package src.main.java.model;

public class Jadwal {
    private String idJadwal;
    private String namaPraktikum;
    private String asisten;
    private String hari;
    private String jam;

    // Constructor, Getter, dan Setter
    public Jadwal(String idJadwal, String namaPraktikum, String asisten, String hari, String jam) {
        this.idJadwal = idJadwal;
        this.namaPraktikum = namaPraktikum;
        this.asisten = asisten;
        this.hari = hari;
        this.jam = jam;
    }

    public String getNamaPraktikum() { return namaPraktikum; }
    public String getAsisten() { return asisten; }
    public String getHari() { return hari; }
    public String getJam() { return jam; }
}