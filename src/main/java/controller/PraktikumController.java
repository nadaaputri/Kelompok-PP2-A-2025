package src.main.java.controller;


import src.main.java.database.KoneksiDB;
import src.main.java.model.Praktikum;

import src.main.java.view.JadwalView;
import src.main.java.view.PraktikumView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class PraktikumController {
    private PraktikumView view;
    private JadwalView jadwalView;


    public PraktikumController(PraktikumView view, JadwalView jadwalView) {
        this.view = view;
        this.jadwalView = jadwalView;

        view.addTambahListener(e -> tambahData());
        view.addEditListener(e -> editData());
        view.addHapusListener(e -> hapusData());
        view.addClearListener(e -> view.clearForm());
        view.addTableMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ambilDataTabel();
            }
        });

        loadData();
    }



    private void loadData() {
        view.getTableModel().setRowCount(0);
        try (Connection conn = KoneksiDB.configDB()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM praktikum");
            while (rs.next()) {
                // Buat objek Praktikum dari hasil query
                Praktikum praktikum = new Praktikum(
                        rs.getInt("id_praktikum"),
                        rs.getString("nama_praktikum"),
                        rs.getString("kode_praktikum"),
                        rs.getInt("semester"),
                        rs.getInt("sks")
                );

                // Tambahkan data ke tabel menggunakan getter dari Praktikum
                view.getTableModel().addRow(new Object[]{
                        praktikum.getId(),
                        praktikum.getNama(),
                        praktikum.getKode(),
                        praktikum.getSemester(),
                        praktikum.getSks()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error Load: " + e.getMessage());
        }
    }


    private void tambahData() {
        if (!validasi()) return;
        try (Connection conn = KoneksiDB.configDB()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO praktikum (nama_praktikum, kode_praktikum, semester, sks) VALUES (?,?,?,?)");
            ps.setString(1, view.getNama()); ps.setString(2, view.getKode());
            ps.setInt(3, Integer.parseInt(view.getSemester())); ps.setInt(4, Integer.parseInt(view.getSks()));
            ps.executeUpdate();
            loadData(); view.clearForm(); JOptionPane.showMessageDialog(view, "Sukses Tambah");
            jadwalView.refreshData();

        } catch (Exception e) { JOptionPane.showMessageDialog(view, "Error: " + e.getMessage()); }
    }

    private void editData() {
        if (view.getId().isEmpty()) return;
        if (!validasi()) return;
        try (Connection conn = KoneksiDB.configDB()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE praktikum SET nama_praktikum=?, kode_praktikum=?, semester=?, sks=? WHERE id_praktikum=?");
            ps.setString(1, view.getNama()); ps.setString(2, view.getKode());
            ps.setInt(3, Integer.parseInt(view.getSemester())); ps.setInt(4, Integer.parseInt(view.getSks()));
            ps.setInt(5, Integer.parseInt(view.getId()));
            ps.executeUpdate();
            loadData(); view.clearForm(); JOptionPane.showMessageDialog(view, "Sukses Edit");
            jadwalView.refreshData();

        } catch (Exception e) { JOptionPane.showMessageDialog(view, "Error: " + e.getMessage()); }
    }

    private void hapusData() {
        if (view.getId().isEmpty()) return;
        if (JOptionPane.showConfirmDialog(view, "Hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try (Connection conn = KoneksiDB.configDB()) {
                conn.createStatement().executeUpdate("DELETE FROM praktikum WHERE id_praktikum=" + view.getId());
                loadData(); view.clearForm();
                jadwalView.refreshData();

            } catch (Exception e) { JOptionPane.showMessageDialog(view, "Error: " + e.getMessage()); }
        }
    }

    private boolean validasi() {
        if (view.getNama().isEmpty() || view.getKode().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nama dan Kode wajib diisi!"); return false;
        }
        try { Integer.parseInt(view.getSemester()); Integer.parseInt(view.getSks()); }
        catch (NumberFormatException e) { JOptionPane.showMessageDialog(view, "Semester dan SKS harus angka!"); return false; }
        return true;
    }

    private void ambilDataTabel() {
        int r = view.getTable().getSelectedRow();
        if (r != -1) {
            // Ambil data dari tabel dan set ke form
            Praktikum praktikum = new Praktikum(
                    Integer.parseInt(view.getTableModel().getValueAt(r, 0).toString()),
                    view.getTableModel().getValueAt(r, 1).toString(),
                    view.getTableModel().getValueAt(r, 2).toString(),
                    Integer.parseInt(view.getTableModel().getValueAt(r, 3).toString()),
                    Integer.parseInt(view.getTableModel().getValueAt(r, 4).toString())
            );

            // Set data ke form menggunakan getter dari Praktikum
            view.setForm(
                    String.valueOf(praktikum.getId()),
                    praktikum.getNama(),
                    praktikum.getKode(),
                    String.valueOf(praktikum.getSemester()),
                    String.valueOf(praktikum.getSks())
            );
        }
    }
}