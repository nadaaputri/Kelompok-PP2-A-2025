package src.main.java.view;

import src.main.java.controller.JadwalController;
import src.main.java.model.ComboItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class JadwalView extends JPanel {
    private JComboBox<ComboItem> cbPraktikum = new JComboBox<>();
    private JComboBox<ComboItem> cbAsisten = new JComboBox<>();
    private JComboBox<String> cbHari = new JComboBox<>(new String[]{"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"});

    private JTextField txtMulai = new JTextField("08:00:00");
    private JTextField txtSelesai = new JTextField("10:00:00");
    private JTextField txtRuang = new JTextField();
    private JTextField txtId = new JTextField();

    // Komponen Filter
    private JComboBox<String> cbFilterHari = new JComboBox<>(new String[]{"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"});
    private JTextField txtCari = new JTextField(15);
    private JButton btnFilterHari = new JButton("Filter Hari");
    private JButton btnCari = new JButton("Cari Praktikum");
    private JButton btnRefresh = new JButton("Lihat Semua");

    private JButton btnTambah = new JButton("Tambah");
    private JButton btnEdit = new JButton("Edit");
    private JButton btnHapus = new JButton("Hapus");

    private JTable table;
    private DefaultTableModel tableModel;

    private JadwalController controller;

    public void setController(JadwalController controller) {
        this.controller = controller;
    }


    public void refreshData() {
        if (controller != null) {
            controller.refreshAll();
        }
    }

    public JadwalView() {
        setLayout(new BorderLayout());

        // --- Form Panel ---
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Kelola Jadwal"));
        txtId.setEditable(false);

        formPanel.add(new JLabel("Praktikum:")); formPanel.add(cbPraktikum);
        formPanel.add(new JLabel("Asisten:")); formPanel.add(cbAsisten);
        formPanel.add(new JLabel("Hari:")); formPanel.add(cbHari);
        formPanel.add(new JLabel("Jam Mulai:")); formPanel.add(txtMulai);
        formPanel.add(new JLabel("Jam Selesai:")); formPanel.add(txtSelesai);
        formPanel.add(new JLabel("Ruang:")); formPanel.add(txtRuang);

        // --- Button Action Panel ---
        JPanel actionPanel = new JPanel();
        actionPanel.add(btnTambah); actionPanel.add(btnEdit); actionPanel.add(btnHapus);

        // --- Filter Panel ---
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter & Pencarian"));
        filterPanel.add(new JLabel("Hari:")); filterPanel.add(cbFilterHari); filterPanel.add(btnFilterHari);
        filterPanel.add(new JLabel(" | Cari:")); filterPanel.add(txtCari); filterPanel.add(btnCari);
        filterPanel.add(btnRefresh);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(formPanel, BorderLayout.CENTER);
        topContainer.add(actionPanel, BorderLayout.SOUTH);

        // --- Table ---
        String[] cols = {"ID", "Praktikum", "Asisten", "Hari", "Mulai", "Selesai", "Ruang"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);

        JPanel mainTop = new JPanel(new BorderLayout());
        mainTop.add(topContainer, BorderLayout.NORTH);
        mainTop.add(filterPanel, BorderLayout.CENTER);

        add(mainTop, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    // Getters for Inputs
    public String getId() { return txtId.getText(); }
    public ComboItem getSelectedPraktikum() { return (ComboItem) cbPraktikum.getSelectedItem(); }
    public ComboItem getSelectedAsisten() { return (ComboItem) cbAsisten.getSelectedItem(); }
    public String getHari() { return cbHari.getSelectedItem().toString(); }
    public String getMulai() { return txtMulai.getText(); }
    public String getSelesai() { return txtSelesai.getText(); }
    public String getRuang() { return txtRuang.getText(); }

    // Getters for Filter
    public String getFilterHari() { return cbFilterHari.getSelectedItem().toString(); }
    public String getKeyword() { return txtCari.getText(); }

    // Accessors for Controller to populate ComboBox
    public JComboBox<ComboItem> getCbPraktikum() { return cbPraktikum; }
    public JComboBox<ComboItem> getCbAsisten() { return cbAsisten; }
    public JComboBox<String> getCbHari() { return cbHari; }

    public DefaultTableModel getTableModel() { return tableModel; }
    public JTable getTable() { return table; }

    // Setters for Form (saat klik tabel)
    public void setForm(String id, String hari, String mulai, String selesai, String ruang) {
        txtId.setText(id);
        cbHari.setSelectedItem(hari);
        txtMulai.setText(mulai);
        txtSelesai.setText(selesai);
        txtRuang.setText(ruang);
    }

    // Listener Registration
    public void addTambahListener(ActionListener l) { btnTambah.addActionListener(l); }
    public void addEditListener(ActionListener l) { btnEdit.addActionListener(l); }
    public void addHapusListener(ActionListener l) { btnHapus.addActionListener(l); }
    public void addFilterListener(ActionListener l) { btnFilterHari.addActionListener(l); }
    public void addCariListener(ActionListener l) { btnCari.addActionListener(l); }
    public void addRefreshListener(ActionListener l) { btnRefresh.addActionListener(l); }
    public void addTableMouseListener(MouseAdapter a) { table.addMouseListener(a); }

}