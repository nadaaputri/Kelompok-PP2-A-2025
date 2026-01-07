package src.main.java.controller;



import src.main.java.database.KoneksiDB;
import src.main.java.model.Asisten;
import src.main.java.view.AsistenView;
import src.main.java.view.JadwalView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AsistenController {
    private AsistenView view;
    private JadwalView jadwalView;

//    public AsistenController(AsistenView view, JadwalView jadwalView) {
//        this.view = view;
//        this.jadwalView = jadwalView;
//
//        // Inisialisasi Event Listener
//        this.view.addTambahListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                tambahData();
//            }
//        });
//
//        this.view.addEditListener(e -> editData());   // Menggunakan Lambda (Java 8+)
//        this.view.addHapusListener(e -> hapusData());
//        this.view.addClearListener(e -> view.clearForm());
//
//        this.view.addTableMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                ambilDataTabel();
//            }
//        });
//
//        loadData(); // Load data saat awal
//    }


    public AsistenController(AsistenView view, JadwalView jadwalView) {
        this.view = view;
        this.jadwalView = jadwalView;

        // Listener initialization
        this.view.addTambahListener(e -> tambahData());
        this.view.addEditListener(e -> editData());
        this.view.addHapusListener(e -> hapusData());
        this.view.addClearListener(e -> view.clearForm());
        this.view.addTableMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ambilDataTabel();
            }
        });

        loadData();
    }
    // --- Logic Bisnis / CRUD ---

    private void loadData() {
        view.getTableModel().setRowCount(0);
        List<Asisten> list = new ArrayList<>();

        try (Connection conn = KoneksiDB.configDB()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM asisten");

            while (rs.next()) {
                list.add(new Asisten(
                        rs.getInt("id_asisten"),
                        rs.getString("nama_asisten"),
                        rs.getString("nim"),
                        rs.getString("no_hp"),
                        rs.getString("email")
                ));
            }

            for (Asisten a : list) {
                view.getTableModel().addRow(new Object[]{
                        a.getId(), a.getNama(), a.getNim(), a.getNoHp(), a.getEmail()
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error Load: " + e.getMessage());
        }
    }


    private void tambahData() {
        // Validasi
        if (view.getNama().isEmpty() || view.getNim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nama dan NIM Wajib diisi!");
            return;
        }

        try (Connection conn = KoneksiDB.configDB()) {
            String sql = "INSERT INTO asisten (nama_asisten, nim, no_hp, email) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, view.getNama());
            ps.setString(2, view.getNim());
            ps.setString(3, view.getHp());
            ps.setString(4, view.getEmail());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(view, "Berhasil Tambah Data");
            view.clearForm();
            loadData();

            jadwalView.refreshData();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error Tambah: " + e.getMessage());
        }
    }




    private void editData() {
        if (view.getId().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih data dari tabel dahulu!");
            return;
        }

        try (Connection conn = KoneksiDB.configDB()) {
            String sql = "UPDATE asisten SET nama_asisten=?, nim=?, no_hp=?, email=? WHERE id_asisten=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, view.getNama());
            ps.setString(2, view.getNim());
            ps.setString(3, view.getHp());
            ps.setString(4, view.getEmail());
            ps.setInt(5, Integer.parseInt(view.getId()));
            ps.executeUpdate();

            JOptionPane.showMessageDialog(view, "Berhasil Edit Data");
            view.clearForm();
            loadData();
            jadwalView.refreshData();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error Edit: " + e.getMessage());
        }
    }

    private void hapusData() {
        if (view.getId().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih data yang akan dihapus!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Yakin hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = KoneksiDB.configDB()) {
                String sql = "DELETE FROM asisten WHERE id_asisten=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(view.getId()));
                ps.executeUpdate();

                JOptionPane.showMessageDialog(view, "Berhasil Hapus Data");
                view.clearForm();
                loadData();
                jadwalView.refreshData();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(view, "Error Hapus: " + e.getMessage());
            }
        }
    }

    private void ambilDataTabel() {
        int row = view.getTable().getSelectedRow();
        if (row != -1) {
            String id = view.getTableModel().getValueAt(row, 0).toString();
            String nama = view.getTableModel().getValueAt(row, 1).toString();
            String nim = view.getTableModel().getValueAt(row, 2).toString();
            String hp = view.getTableModel().getValueAt(row, 3).toString();
            String email = view.getTableModel().getValueAt(row, 4).toString();

            view.setForm(id, nama, nim, hp, email);
        }
    }
}
