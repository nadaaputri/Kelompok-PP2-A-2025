package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDB {
    private static Connection mysqlconfig;
    public static Connection configDB() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/db_jadwal_praktikum"; // Sesuaikan nama database
            String user = "root"; 
            String pass = "";
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            mysqlconfig = DriverManager.getConnection(url, user, pass);            
        } catch (Exception e) {
            System.err.println("Koneksi gagal " + e.getMessage());
        }
        return mysqlconfig;
    }
}