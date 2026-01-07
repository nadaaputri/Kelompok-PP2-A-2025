package src.main.java.controller;


import src.main.java.database.KoneksiDB;
import src.main.java.view.JadwalView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

import src.main.java.model.*;
import java.util.ArrayList;
import java.util.List;


public class JadwalController {
    private final JadwalView view;

    public JadwalController(JadwalView view) {
        this.view = view;
        loadComboBoxData(); // Isi dropdown dulu
        loadTableData("");  // Load semua data

        view.addTambahListener(e -> simpan("INSERT"));
        view.addEditListener(e -> simpan("UPDATE"));
        view.addHapusListener(e -> hapus());

        // Fitur Filter & Search
        view.addRefreshListener(e -> loadTableData(""));
        view.addFilterListener(e -> loadTableData("WHERE j.hari = '" + view.getFilterHari() + "'"));
        view.addCariListener(e -> loadTableData("WHERE p.nama_praktikum LIKE '%" + view.getKeyword() + "%'"));

        view.addTableMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { ambilDataTabel(); }
        });
    }

    public void refreshAll() {
        loadComboBoxData();
        loadTableData("");
    }

    private void loadComboBoxData() {
        try (Connection conn = KoneksiDB.configDB()) {
            view.getCbPraktikum().removeAllItems();
            view.getCbAsisten().removeAllItems();

            // Isi Combo Praktikum
            ResultSet rsP = conn.createStatement().executeQuery("SELECT id_praktikum, nama_praktikum FROM praktikum");
            while (rsP.next()) {
                view.getCbPraktikum().addItem(new ComboItem(rsP.getInt(1), rsP.getString(2)));
            }

            // Isi Combo Asisten
            ResultSet rsA = conn.createStatement().executeQuery("SELECT id_asisten, nama_asisten FROM asisten");
            while (rsA.next()) {
                view.getCbAsisten().addItem(new ComboItem(rsA.getInt(1), rsA.getString(2)));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error Load Combo: " + e.getMessage());
        }
    }

    private void loadTableData(String whereClause) {
        view.getTableModel().setRowCount(0);
        List<JadwalPraktikum> list = new ArrayList<>();

        String sql = "SELECT j.*, p.nama_praktikum, a.nama_asisten " +
                "FROM jadwal_praktikum j " +
                "JOIN praktikum p ON j.id_praktikum = p.id_praktikum " +
                "JOIN asisten a ON j.id_asisten = a.id_asisten " + whereClause;

        try (Connection conn = KoneksiDB.configDB();
             ResultSet rs = conn.createStatement().executeQuery(sql)) {

            while (rs.next()) {
                Praktikum p = new Praktikum(
                        rs.getInt("id_praktikum"),
                        rs.getString("nama_praktikum"),
                        null, 0, 0
                );

                Asisten a = new Asisten(
                        rs.getInt("id_asisten"),
                        rs.getString("nama_asisten"),
                        null, null, null
                );

                list.add(new JadwalPraktikum(
                        rs.getInt("id_jadwal"),
                        p,
                        a,
                        rs.getString("hari"),
                        rs.getTime("jam_mulai"),
                        rs.getTime("jam_selesai"),
                        rs.getString("ruangan")
                ));
            }

            for (JadwalPraktikum j : list) {
                view.getTableModel().addRow(new Object[]{
                        j.getId(),
                        j.getPraktikum().getNama(),
                        j.getAsisten().getNama(),
                        j.getHari(),
                        j.getJamMulai(),
                        j.getJamSelesai(),
                        j.getRuang()
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error Load: " + e.getMessage());
        }
    }


    private void simpan(String type) {
        ComboItem praktikum = view.getSelectedPraktikum();
        ComboItem asisten = view.getSelectedAsisten();

        if (praktikum == null || asisten == null) {
            JOptionPane.showMessageDialog(view, "Pilih Praktikum dan Asisten!"); return;
        }

        // Validasi field tidak boleh kosong
        if (view.getHari().isEmpty() || view.getMulai().isEmpty() || view.getSelesai().isEmpty() || view.getRuang().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Semua field harus diisi!"); return;
        }

        try (Connection conn = KoneksiDB.configDB()) {
            PreparedStatement ps;
            
            // Validasi duplikat untuk INSERT atau UPDATE
            String checkSql;
            PreparedStatement checkPs;
            
            if (type.equals("INSERT")) {
                // Cek konflik jadwal: praktikum yang sama, hari yang sama, jam yang overlap
                checkSql = "SELECT COUNT(*) FROM jadwal_praktikum WHERE id_praktikum = ? AND hari = ? " +
                          "AND ((jam_mulai <= ? AND jam_selesai > ?) OR (jam_mulai < ? AND jam_selesai >= ?) OR (jam_mulai >= ? AND jam_selesai <= ?))";
                checkPs = conn.prepareStatement(checkSql);
                checkPs.setInt(1, praktikum.getId());
                checkPs.setString(2, view.getHari());
                checkPs.setString(3, view.getMulai());
                checkPs.setString(4, view.getMulai());
                checkPs.setString(5, view.getSelesai());
                checkPs.setString(6, view.getSelesai());
                checkPs.setString(7, view.getMulai());
                checkPs.setString(8, view.getSelesai());
                
                ResultSet rs = checkPs.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(view, "Jadwal konflik! Praktikum ini sudah ada di hari dan waktu yang sama atau berdekatan.");
                    return;
                }
                
                // Cek konflik asisten: asisten yang sama, hari yang sama, jam yang overlap
                checkSql = "SELECT COUNT(*) FROM jadwal_praktikum WHERE id_asisten = ? AND hari = ? " +
                          "AND ((jam_mulai <= ? AND jam_selesai > ?) OR (jam_mulai < ? AND jam_selesai >= ?) OR (jam_mulai >= ? AND jam_selesai <= ?))";
                checkPs = conn.prepareStatement(checkSql);
                checkPs.setInt(1, asisten.getId());
                checkPs.setString(2, view.getHari());
                checkPs.setString(3, view.getMulai());
                checkPs.setString(4, view.getMulai());
                checkPs.setString(5, view.getSelesai());
                checkPs.setString(6, view.getSelesai());
                checkPs.setString(7, view.getMulai());
                checkPs.setString(8, view.getSelesai());
                
                rs = checkPs.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(view, "Jadwal konflik! Asisten ini sudah mengajar di hari dan waktu yang sama atau berdekatan.");
                    return;
                }
                
                // Cek konflik ruangan: ruangan yang sama, hari yang sama, jam yang overlap
                checkSql = "SELECT COUNT(*) FROM jadwal_praktikum WHERE ruangan = ? AND hari = ? " +
                          "AND ((jam_mulai <= ? AND jam_selesai > ?) OR (jam_mulai < ? AND jam_selesai >= ?) OR (jam_mulai >= ? AND jam_selesai <= ?))";
                checkPs = conn.prepareStatement(checkSql);
                checkPs.setString(1, view.getRuang());
                checkPs.setString(2, view.getHari());
                checkPs.setString(3, view.getMulai());
                checkPs.setString(4, view.getMulai());
                checkPs.setString(5, view.getSelesai());
                checkPs.setString(6, view.getSelesai());
                checkPs.setString(7, view.getMulai());
                checkPs.setString(8, view.getSelesai());
                
                rs = checkPs.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(view, "Jadwal konflik! Ruangan ini sudah digunakan di hari dan waktu yang sama atau berdekatan.");
                    return;
                }
                
                ps = conn.prepareStatement("INSERT INTO jadwal_praktikum (id_praktikum, id_asisten, hari, jam_mulai, jam_selesai, ruangan) VALUES (?,?,?,?,?,?)");
            } else {
                if (view.getId().isEmpty()) return;
                
                int currentId = Integer.parseInt(view.getId());
                
                // Cek konflik untuk UPDATE (kecuali record yang sedang diedit)
                checkSql = "SELECT COUNT(*) FROM jadwal_praktikum WHERE id_praktikum = ? AND hari = ? AND id_jadwal != ? " +
                          "AND ((jam_mulai <= ? AND jam_selesai > ?) OR (jam_mulai < ? AND jam_selesai >= ?) OR (jam_mulai >= ? AND jam_selesai <= ?))";
                checkPs = conn.prepareStatement(checkSql);
                checkPs.setInt(1, praktikum.getId());
                checkPs.setString(2, view.getHari());
                checkPs.setInt(3, currentId);
                checkPs.setString(4, view.getMulai());
                checkPs.setString(5, view.getMulai());
                checkPs.setString(6, view.getSelesai());
                checkPs.setString(7, view.getSelesai());
                checkPs.setString(8, view.getMulai());
                checkPs.setString(9, view.getSelesai());
                
                ResultSet rs = checkPs.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(view, "Jadwal konflik! Praktikum ini sudah ada di hari dan waktu yang sama atau berdekatan.");
                    return;
                }
                
                checkSql = "SELECT COUNT(*) FROM jadwal_praktikum WHERE id_asisten = ? AND hari = ? AND id_jadwal != ? " +
                          "AND ((jam_mulai <= ? AND jam_selesai > ?) OR (jam_mulai < ? AND jam_selesai >= ?) OR (jam_mulai >= ? AND jam_selesai <= ?))";
                checkPs = conn.prepareStatement(checkSql);
                checkPs.setInt(1, asisten.getId());
                checkPs.setString(2, view.getHari());
                checkPs.setInt(3, currentId);
                checkPs.setString(4, view.getMulai());
                checkPs.setString(5, view.getMulai());
                checkPs.setString(6, view.getSelesai());
                checkPs.setString(7, view.getSelesai());
                checkPs.setString(8, view.getMulai());
                checkPs.setString(9, view.getSelesai());
                
                rs = checkPs.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(view, "Jadwal konflik! Asisten ini sudah mengajar di hari dan waktu yang sama atau berdekatan.");
                    return;
                }
                
                checkSql = "SELECT COUNT(*) FROM jadwal_praktikum WHERE ruangan = ? AND hari = ? AND id_jadwal != ? " +
                          "AND ((jam_mulai <= ? AND jam_selesai > ?) OR (jam_mulai < ? AND jam_selesai >= ?) OR (jam_mulai >= ? AND jam_selesai <= ?))";
                checkPs = conn.prepareStatement(checkSql);
                checkPs.setString(1, view.getRuang());
                checkPs.setString(2, view.getHari());
                checkPs.setInt(3, currentId);
                checkPs.setString(4, view.getMulai());
                checkPs.setString(5, view.getMulai());
                checkPs.setString(6, view.getSelesai());
                checkPs.setString(7, view.getSelesai());
                checkPs.setString(8, view.getMulai());
                checkPs.setString(9, view.getSelesai());
                
                rs = checkPs.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(view, "Jadwal konflik! Ruangan ini sudah digunakan di hari dan waktu yang sama atau berdekatan.");
                    return;
                }
                
                ps = conn.prepareStatement("UPDATE jadwal_praktikum SET id_praktikum=?, id_asisten=?, hari=?, jam_mulai=?, jam_selesai=?, ruangan=? WHERE id_jadwal=?");
                ps.setInt(7, currentId);
            }

            ps.setInt(1, praktikum.getId());
            ps.setInt(2, asisten.getId());
            ps.setString(3, view.getHari());
            ps.setString(4, view.getMulai());
            ps.setString(5, view.getSelesai());
            ps.setString(6, view.getRuang());

            ps.executeUpdate();
            loadTableData("");
            JOptionPane.showMessageDialog(view, "Berhasil!");
        } catch (SQLException e) { JOptionPane.showMessageDialog(view, "Error: " + e.getMessage()); }
    }

    private void hapus() {
        if (view.getId().isEmpty()) { JOptionPane.showMessageDialog(view, "Pilih data dulu!"); return; }
        if (JOptionPane.showConfirmDialog(view, "Hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try (Connection conn = KoneksiDB.configDB()) {
                conn.createStatement().executeUpdate("DELETE FROM jadwal_praktikum WHERE id_jadwal=" + view.getId());
                loadTableData("");
            } catch (SQLException e) { JOptionPane.showMessageDialog(view, "Error: " + e.getMessage()); }
        }
    }

    private void ambilDataTabel() {
        int r = view.getTable().getSelectedRow();
        if (r != -1) {
            String id = view.getTableModel().getValueAt(r, 0).toString();
            // Note: Praktikum dan Asisten di combo box agak tricky untuk diset otomatis berdasarkan nama dari tabel
            // Untuk simplifikasi, kita set form text biasa saja, user harus pilih ulang combobox jika mau edit.
            view.setForm(id,
                    view.getTableModel().getValueAt(r, 3).toString(),
                    view.getTableModel().getValueAt(r, 4).toString(),
                    view.getTableModel().getValueAt(r, 5).toString(),
                    view.getTableModel().getValueAt(r, 6).toString()
            );
        }
    }
}
