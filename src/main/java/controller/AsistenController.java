package src.main.java.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import src.main.java.database.KoneksiDB;
import src.main.java.model.Asisten;
import src.main.java.view.AsistenView;
import src.main.java.view.JadwalView;

public class AsistenController {
    private AsistenView view;
    private JadwalView jadwalView;

    public AsistenController(AsistenView view, JadwalView jadwalView) {
        this.view = view;
        this.jadwalView = jadwalView;
        this.view.addTambahListener((e) -> {
            this.tambahData();
        });
        this.view.addEditListener((e) -> {
            this.editData();
        });
        this.view.addHapusListener((e) -> {
            this.hapusData();
        });
        this.view.addClearListener((e) -> {
            view.clearForm();
        });
        this.view.addTableMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                AsistenController.this.ambilDataTabel();
            }
        });
        this.loadData();
    }

    private void loadData() {
        this.view.getTableModel().setRowCount(0);
        List<Asisten> list = new ArrayList();

        try {
            Connection conn = KoneksiDB.configDB();

            try {
                ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM asisten");

                while (rs.next()) {
                    list.add(new Asisten(rs.getInt("id_asisten"), rs.getString("nama_asisten"), rs.getString("nim"),
                            rs.getString("no_hp"), rs.getString("email")));
                }

                Iterator var4 = list.iterator();

                while (var4.hasNext()) {
                    Asisten a = (Asisten) var4.next();
                    this.view.getTableModel()
                            .addRow(new Object[] { a.getId(), a.getNama(), a.getNim(), a.getNoHp(), a.getEmail() });
                }
            } catch (Throwable var7) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }
                }

                throw var7;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException var8) {
            JOptionPane.showMessageDialog(this.view, "Error Load: " + var8.getMessage());
        }

    }

    private void tambahData() {
        if (!this.view.getNama().isEmpty() && !this.view.getNim().isEmpty()) {
            try {
                Connection conn = KoneksiDB.configDB();

                try {
                    String sql = "INSERT INTO asisten (nama_asisten, nim, no_hp, email) VALUES (?, ?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, this.view.getNama());
                    ps.setString(2, this.view.getNim());
                    ps.setString(3, this.view.getHp());
                    ps.setString(4, this.view.getEmail());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this.view, "Berhasil Tambah Data");
                    this.view.clearForm();
                    this.loadData();
                    this.jadwalView.refreshData();
                } catch (Throwable var5) {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (Throwable var4) {
                            var5.addSuppressed(var4);
                        }
                    }

                    throw var5;
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var6) {
                JOptionPane.showMessageDialog(this.view, "Error Tambah: " + var6.getMessage());
            }

        } else {
            JOptionPane.showMessageDialog(this.view, "Nama dan NIM Wajib diisi!");
        }
    }

    private void editData() {
        if (this.view.getId().isEmpty()) {
            JOptionPane.showMessageDialog(this.view, "Pilih data dari tabel dahulu!");
        } else {
            try {
                Connection conn = KoneksiDB.configDB();

                try {
                    String sql = "UPDATE asisten SET nama_asisten=?, nim=?, no_hp=?, email=? WHERE id_asisten=?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, this.view.getNama());
                    ps.setString(2, this.view.getNim());
                    ps.setString(3, this.view.getHp());
                    ps.setString(4, this.view.getEmail());
                    ps.setInt(5, Integer.parseInt(this.view.getId()));
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this.view, "Berhasil Edit Data");
                    this.view.clearForm();
                    this.loadData();
                    this.jadwalView.refreshData();
                } catch (Throwable var5) {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (Throwable var4) {
                            var5.addSuppressed(var4);
                        }
                    }

                    throw var5;
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var6) {
                JOptionPane.showMessageDialog(this.view, "Error Edit: " + var6.getMessage());
            }

        }
    }

    private void hapusData() {
        if (this.view.getId().isEmpty()) {
            JOptionPane.showMessageDialog(this.view, "Pilih data yang akan dihapus!");
        } else {
            int confirm = JOptionPane.showConfirmDialog(this.view, "Yakin hapus data ini?", "Konfirmasi", 0);
            if (confirm == 0) {
                try {
                    Connection conn = KoneksiDB.configDB();

                    try {
                        String sql = "DELETE FROM asisten WHERE id_asisten=?";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setInt(1, Integer.parseInt(this.view.getId()));
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(this.view, "Berhasil Hapus Data");
                        this.view.clearForm();
                        this.loadData();
                        this.jadwalView.refreshData();
                    } catch (Throwable var6) {
                        if (conn != null) {
                            try {
                                conn.close();
                            } catch (Throwable var5) {
                                var6.addSuppressed(var5);
                            }
                        }

                        throw var6;
                    }

                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException var7) {
                    JOptionPane.showMessageDialog(this.view, "Error Hapus: " + var7.getMessage());
                }
            }

        }
    }

    private void ambilDataTabel() {
        int row = this.view.getTable().getSelectedRow();
        if (row != -1) {
            String id = this.view.getTableModel().getValueAt(row, 0).toString();
            String nama = this.view.getTableModel().getValueAt(row, 1).toString();
            String nim = this.view.getTableModel().getValueAt(row, 2).toString();
            String hp = this.view.getTableModel().getValueAt(row, 3).toString();
            String email = this.view.getTableModel().getValueAt(row, 4).toString();
            this.view.setForm(id, nama, nim, hp, email);
        }

    }
}
