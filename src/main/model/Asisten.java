package src.main.model;

public class Asisten {
   private int id;
   private String nama;
   private String nim;
   private String noHp;
   private String email;

   public Asisten(int id, String nama, String nim, String noHp, String email) {
      this.id = id;
      this.nama = nama;
      this.nim = nim;
      this.noHp = noHp;
      this.email = email;
   }

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getNama() {
      return this.nama;
   }

   public void setNama(String nama) {
      this.nama = nama;
   }

   public String getNim() {
      return this.nim;
   }

   public void setNim(String nim) {
      this.nim = nim;
   }

   public String getNoHp() {
      return this.noHp;
   }

   public void setNoHp(String noHp) {
      this.noHp = noHp;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }
}
