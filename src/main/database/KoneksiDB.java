package src.main.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class KoneksiDB {
    private static Connection mysqlconfig;

    public static Connection configDB() throws SQLException {
        try {
            // URL database
            String url = "jdbc:mysql://localhost:3306/db_jadwal_praktikum";
            String user = "root";
            String pass = "";



            // Registrasi Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Buat koneksi
            mysqlconfig = DriverManager.getConnection(url, user, pass);

            System.out.println("KONEKSI BERHASIL");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi Gagal: " + e.getMessage());
        }
        return mysqlconfig;
    }
}
