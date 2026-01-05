package src.main.java.controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import src.main.java.database.KoneksiDB;
import src.main.java.model.Asisten;

public class AsistenController {
    public List<Asisten> getAll() {
        List<Asisten> list = new ArrayList<>();
        String sql = "SELECT * FROM asisten";
        try (Connection conn = KoneksiDB.configDB();
                Statement stm = conn.createStatement();
                ResultSet res = stm.executeQuery(sql)) {
            while (res.next()) {
                list.add(new Asisten(
                        res.getInt("id_asisten"),
                        res.getString("nama_asisten"),
                        res.getString("nim"),
                        res.getString("no_hp"),
                        res.getString("email")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(Asisten asisten) throws SQLException {
        // Validasi Nama tidak boleh kosong
        if (asisten.getNama_asisten().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nama Asisten tidak boleh kosong");
            return;
        }

        // Validasi nama hanya boleh huruf dan spasi
        if (!asisten.getNama_asisten().matches("^[a-zA-Z\\s]+$")) {
            JOptionPane.showMessageDialog(null, "Nama berupa huruf!");
            return;
        }

        // Validasi NIM tidak boleh kosong
        if (asisten.getNim().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NIM tidak boleh kosong");
            return;
        }

        // Validasi NIM duplikat
        if (checkNimExists(asisten.getNim())) {
            JOptionPane.showMessageDialog(null, "NIM " + asisten.getNim() + " sudah terdaftar!");
            return;
        }

        // Validasi no hp harus berupa angka
        if (!asisten.getNo_hp().matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "No HP harus berupa angka!");
            return;
        }

        String sql = "INSERT INTO asisten (nama_asisten, nim, no_hp, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = KoneksiDB.configDB();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, asisten.getNama_asisten());
            pst.setString(2, asisten.getNim());
            pst.setString(3, asisten.getNo_hp());
            pst.setString(4, asisten.getEmail());
            pst.execute();
        }
    }

    public void update(Asisten asisten) throws SQLException {
        // Validasi Nama tidak boleh kosong
        if (asisten.getNama_asisten().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nama Asisten tidak boleh kosong");
            return;
        }

        // Validasi nama hanya boleh huruf dan spasi
        if (!asisten.getNama_asisten().matches("^[a-zA-Z\\s]+$")) {
            JOptionPane.showMessageDialog(null, "Nama berupa huruf!");
            return;
        }

        // Validasi NIM tidak boleh kosong
        if (asisten.getNim().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NIM tidak boleh kosong");
            return;
        }

        // Validasi no hp harus berupa angka
        if (!asisten.getNo_hp().matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "No HP harus berupa angka!");
            return;
        }

        String sql = "UPDATE asisten SET nama_asisten = ?, nim = ?, no_hp = ?, email = ? WHERE id_asisten = ?";
        try (Connection conn = KoneksiDB.configDB();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, asisten.getNama_asisten());
            pst.setString(2, asisten.getNim());
            pst.setString(3, asisten.getNo_hp());
            pst.setString(4, asisten.getEmail());
            pst.setInt(5, asisten.getId_asisten());
            pst.executeUpdate();
        }
    }

    public void delete(int id_asisten) throws SQLException {
        String sql = "DELETE FROM asisten WHERE id_asisten = ?";
        try (Connection conn = KoneksiDB.configDB();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id_asisten);
            pst.execute();
        }
    }

    public boolean checkNimExists(String nim) {
        String sql = "SELECT count(*) FROM asisten WHERE nim = ?";
        try (Connection conn = KoneksiDB.configDB();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, nim);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    return res.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
