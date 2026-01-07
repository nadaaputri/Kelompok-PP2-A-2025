package src.main.java.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class AsistenView extends JPanel {
    // Komponen GUI dibuat public atau ada getter agar bisa diakses Controller
    private JTextField txtNama = new JTextField();
    private JTextField txtNim = new JTextField();
    private JTextField txtHp = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtId = new JTextField(); // Hidden ID

    private JButton btnTambah = new JButton("Tambah");
    private JButton btnEdit = new JButton("Edit");
    private JButton btnHapus = new JButton("Hapus");
    private JButton btnClear = new JButton("Clear");

    private JTable table;
    private DefaultTableModel tableModel;

    public AsistenView() {
        setLayout(new BorderLayout());

        // --- Panel Form ---
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Data Asisten"));

        txtId.setEditable(false); // ID tidak boleh diedit manual

        formPanel.add(new JLabel("Nama:")); formPanel.add(txtNama);
        formPanel.add(new JLabel("NIM:")); formPanel.add(txtNim);
        formPanel.add(new JLabel("No HP:")); formPanel.add(txtHp);
        formPanel.add(new JLabel("Email:")); formPanel.add(txtEmail);
        // ID disembunyikan dari grid tapi tetap ada di object logic

        // --- Panel Tombol ---
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnTambah);
        btnPanel.add(btnEdit);
        btnPanel.add(btnHapus);
        btnPanel.add(btnClear);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(btnPanel, BorderLayout.SOUTH);

        // --- Tabel ---
        String[] kolom = {"ID", "Nama", "NIM", "HP", "Email"};
        tableModel = new DefaultTableModel(kolom, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // --- Getter Data dari Form ---
    public String getNama() { return txtNama.getText(); }
    public String getNim() { return txtNim.getText(); }
    public String getHp() { return txtHp.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getId() { return txtId.getText(); }

    // --- Setter Data ke Form (misal saat klik tabel) ---
    public void setForm(String id, String nama, String nim, String hp, String email) {
        txtId.setText(id);
        txtNama.setText(nama);
        txtNim.setText(nim);
        txtHp.setText(hp);
        txtEmail.setText(email);
    }

    public void clearForm() {
        txtId.setText(""); txtNama.setText(""); txtNim.setText(""); txtHp.setText(""); txtEmail.setText("");
    }

    public DefaultTableModel getTableModel() { return tableModel; }
    public JTable getTable() { return table; }

    // --- Listener Injection (Agar Controller bisa menghandle event) ---
    public void addTambahListener(ActionListener listener) { btnTambah.addActionListener(listener); }
    public void addEditListener(ActionListener listener) { btnEdit.addActionListener(listener); }
    public void addHapusListener(ActionListener listener) { btnHapus.addActionListener(listener); }
    public void addClearListener(ActionListener listener) { btnClear.addActionListener(listener); }
    public void addTableMouseListener(MouseAdapter adapter) { table.addMouseListener(adapter); }
}