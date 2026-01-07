package src.main.java.controller;

import src.main.java.database.KoneksiDB;
import src.main.java.model.Asisten;
import src.main.java.model.Jadwal;
import src.main.java.model.Praktikum;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JadwalController {

    public void insert(Jadwal jadwal) {
        if (!validasi(jadwal)) return;

        try (Connection conn = KoneksiDB.configDB()) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO jadwal (id_asisten, id_praktikum, hari, jam) VALUES (?,?,?,?)"
            );
            ps.setInt(1, jadwal.getIdAsisten());
            ps.setInt(2, jadwal.getIdPraktikum());
            ps.setString(3, jadwal.getHari());
            ps.setString(4, jadwal.getJam());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Sukses Tambah Jadwal");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void update(Jadwal jadwal) {
        try (Connection conn = KoneksiDB.configDB()) {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE jadwal SET id_asisten=?, id_praktikum=?, hari=?, jam=? WHERE id_jadwal=?"
            );
            ps.setInt(1, jadwal.getIdAsisten());
            ps.setInt(2, jadwal.getIdPraktikum());
            ps.setString(3, jadwal.getHari());
            ps.setString(4, jadwal.getJam());
            ps.setInt(5, jadwal.getIdJadwal());
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void delete(int id) {
        try (Connection conn = KoneksiDB.configDB()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM jadwal WHERE id_jadwal=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public List<Jadwal> getAll() {
        List<Jadwal> list = new ArrayList<>();
        try (Connection conn = KoneksiDB.configDB()) {
            String sql = "SELECT j.*, a.nama as nama_asisten, p.nama_praktikum " +
                    "FROM jadwal j " +
                    "JOIN asisten a ON j.id_asisten = a.id_asisten " +
                    "JOIN praktikum p ON j.id_praktikum = p.id_praktikum";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                list.add(new Jadwal(
                        rs.getInt("id_jadwal"),
                        rs.getInt("id_asisten"),
                        rs.getInt("id_praktikum"),
                        rs.getString("hari"),
                        rs.getString("jam")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Jadwal> search(String keyword) {
        List<Jadwal> list = new ArrayList<>();
        try (Connection conn = KoneksiDB.configDB()) {
            String sql = "SELECT j.* FROM jadwal j " +
                    "JOIN asisten a ON j.id_asisten = a.id_asisten " +
                    "WHERE j.hari LIKE ? OR a.nama LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Jadwal(
                        rs.getInt("id_jadwal"),
                        rs.getInt("id_asisten"),
                        rs.getInt("id_praktikum"),
                        rs.getString("hari"),
                        rs.getString("jam")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Asisten> getListAsisten() {
        List<Asisten> list = new ArrayList<>();
        try (Connection conn = KoneksiDB.configDB()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM asisten");
            while (rs.next()) {
                list.add(new Asisten(rs.getInt("id_asisten"), rs.getString("nama")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Praktikum> getListPraktikum() {
        List<Praktikum> list = new ArrayList<>();
        try (Connection conn = KoneksiDB.configDB()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM praktikum");
            while (rs.next()) {
                list.add(new Praktikum(
                        rs.getInt("id_praktikum"),
                        rs.getString("nama_praktikum"),
                        rs.getString("kode_praktikum"),
                        rs.getInt("semester"),
                        rs.getInt("sks")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private boolean validasi(Jadwal jadwal) {
        if (jadwal.getHari().isEmpty() || jadwal.getJam().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ruangan & Jam wajib diisi!");
            return false;
        }
        return true;
    }
}
