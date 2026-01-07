package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import controller.AsistenController;

public class mainView extends JFrame {
    // --- Komponen Asisten ---
    public JTable tableAsisten;
    public JTextField txtNama, txtNIM, txtEmail, txtNoHP, txtCari;
    public JButton btnSimpanAsisten, btnUbahAsisten, btnHapusAsisten, btnResetAsisten, btnCariAsisten;
    
    // --- Komponen Jadwal ---
    public JTable tableJadwal;
    public JTextField txtIdJadwal, txtIdPraktikum, txtIdAsisten, txtJamMulai, txtJamSelesai, txtRuang;
    public JComboBox<String> cbHari;
    public JButton btnTambahJadwal, btnRefreshJadwal, btnHapusJadwal;

    private AsistenController asistenController;

    public mainView() {
        setTitle("Sistem Manajemen Praktikum 2025");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menggunakan TabbedPane untuk memisahkan modul
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tambahkan Tab
        tabbedPane.addTab("Data Asisten", createAsistenPanel());
        tabbedPane.addTab("Jadwal Praktikum", createJadwalPanel());

        add(tabbedPane);

        // Integrasi Controller (Hanya untuk Asisten sesuai kode awal Anda)
        asistenController = new AsistenController(this);
        btnCariAsisten.addActionListener(e -> asistenController.search());
    }

    // --- PANEL ASISTEN ---
    private JPanel createAsistenPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input Form
        JPanel panelInput = new JPanel(new GridLayout(4, 2, 5, 5));
        panelInput.setBorder(BorderFactory.createTitledBorder("Form Input Asisten"));
        
        panelInput.add(new JLabel("Nama:"));
        txtNama = new JTextField(); panelInput.add(txtNama);
        panelInput.add(new JLabel("NIM:"));
        txtNIM = new JTextField(); panelInput.add(txtNIM);
        panelInput.add(new JLabel("Email:"));
        txtEmail = new JTextField(); panelInput.add(txtEmail);
        panelInput.add(new JLabel("No HP:"));
        txtNoHP = new JTextField(); panelInput.add(txtNoHP);

        // Tombol Aksi
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnSimpanAsisten = new JButton("Simpan");
        btnUbahAsisten = new JButton("Ubah");
        btnHapusAsisten = new JButton("Hapus");
        btnResetAsisten = new JButton("Reset");
        panelTombol.add(btnSimpanAsisten);
        panelTombol.add(btnUbahAsisten);
        panelTombol.add(btnHapusAsisten);
        panelTombol.add(btnResetAsisten);

        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.add(panelInput, BorderLayout.CENTER);
        panelAtas.add(panelTombol, BorderLayout.SOUTH);

        // Tabel
        tableAsisten = new JTable(new DefaultTableModel(
            new Object[]{"NIM", "Nama", "Email", "No HP"}, 0
        ));
        JScrollPane scrollPane = new JScrollPane(tableAsisten);

        // Cari
        JPanel panelCari = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtCari = new JTextField(20);
        btnCariAsisten = new JButton("Cari");
        panelCari.add(new JLabel("Cari Nama:"));
        panelCari.add(txtCari);
        panelCari.add(btnCariAsisten);

        panel.add(panelAtas, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelCari, BorderLayout.SOUTH);

        return panel;
    }

    // --- PANEL JADWAL ---
    private JPanel createJadwalPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input Form
        JPanel panelInput = new JPanel(new GridLayout(4, 4, 10, 10));
        panelInput.setBorder(BorderFactory.createTitledBorder("Form Input Jadwal"));

        panelInput.add(new JLabel("ID Jadwal:"));
        txtIdJadwal = new JTextField(); panelInput.add(txtIdJadwal);
        
        panelInput.add(new JLabel("ID Praktikum:"));
        txtIdPraktikum = new JTextField(); panelInput.add(txtIdPraktikum);

        panelInput.add(new JLabel("ID Asisten:"));
        txtIdAsisten = new JTextField(); panelInput.add(txtIdAsisten);

        panelInput.add(new JLabel("Hari:"));
        cbHari = new JComboBox<>(new String[]{"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"});
        panelInput.add(cbHari);

        panelInput.add(new JLabel("Jam Mulai:"));
        txtJamMulai = new JTextField(); panelInput.add(txtJamMulai);

        panelInput.add(new JLabel("Jam Selesai:"));
        txtJamSelesai = new JTextField(); panelInput.add(txtJamSelesai);

        panelInput.add(new JLabel("Ruang:"));
        txtRuang = new JTextField(); panelInput.add(txtRuang);

        // Tombol Aksi Jadwal
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnTambahJadwal = new JButton("Tambah Jadwal");
        btnRefreshJadwal = new JButton("Refresh");
        btnHapusJadwal = new JButton("Hapus");
        panelTombol.add(btnTambahJadwal);
        panelTombol.add(btnRefreshJadwal);
        panelTombol.add(btnHapusJadwal);

        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.add(panelInput, BorderLayout.CENTER);
        panelAtas.add(panelTombol, BorderLayout.SOUTH);

        // Tabel Jadwal
        String[] kolom = {"ID Jadwal", "ID Praktikum", "ID Asisten", "Hari", "Mulai", "Selesai", "Ruang"};
        tableJadwal = new JTable(new DefaultTableModel(kolom, 0));
        JScrollPane scrollPane = new JScrollPane(tableJadwal);

        panel.add(panelAtas, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        // Menjalankan aplikasi
        SwingUtilities.invokeLater(() -> {
            new mainView().setVisible(true);
        });
    }
}