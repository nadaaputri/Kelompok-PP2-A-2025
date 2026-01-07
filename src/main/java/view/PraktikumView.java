package src.main.java.view;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class PraktikumView extends JPanel {
    private JTextField txtNama = new JTextField();
    private JTextField txtKode = new JTextField();
    private JTextField txtSemester = new JTextField();
    private JTextField txtSks = new JTextField();
    private JTextField txtId = new JTextField(); // Hidden ID

    private JButton btnTambah = new JButton("Tambah");
    private JButton btnEdit = new JButton("Edit");
    private JButton btnHapus = new JButton("Hapus");
    private JButton btnClear = new JButton("Clear");

    private JTable table;
    private DefaultTableModel tableModel;

    public PraktikumView() {
        setLayout(new BorderLayout());

        // Form
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Input Data Praktikum"));

        txtId.setEditable(false);
        formPanel.add(new JLabel("Nama Praktikum:")); formPanel.add(txtNama);
        formPanel.add(new JLabel("Kode Praktikum:")); formPanel.add(txtKode);
        formPanel.add(new JLabel("Semester:")); formPanel.add(txtSemester);
        formPanel.add(new JLabel("SKS:")); formPanel.add(txtSks);

        // Buttons
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnTambah); btnPanel.add(btnEdit);
        btnPanel.add(btnHapus); btnPanel.add(btnClear);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(btnPanel, BorderLayout.SOUTH);

        // Table
        String[] columns = {"ID", "Nama", "Kode", "Semester", "SKS"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Getters
    public String getId() { return txtId.getText(); }
    public String getNama() { return txtNama.getText(); }
    public String getKode() { return txtKode.getText(); }
    public String getSemester() { return txtSemester.getText(); }
    public String getSks() { return txtSks.getText(); }

    public DefaultTableModel getTableModel() { return tableModel; }
    public JTable getTable() { return table; }

    // Setters
    public void setForm(String id, String nama, String kode, String sem, String sks) {
        txtId.setText(id); txtNama.setText(nama); txtKode.setText(kode);
        txtSemester.setText(sem); txtSks.setText(sks);
    }
    public void clearForm() {
        txtId.setText(""); txtNama.setText(""); txtKode.setText("");
        txtSemester.setText(""); txtSks.setText("");
    }

    // Listeners
    public void addTambahListener(ActionListener l) { btnTambah.addActionListener(l); }
    public void addEditListener(ActionListener l) { btnEdit.addActionListener(l); }
    public void addHapusListener(ActionListener l) { btnHapus.addActionListener(l); }
    public void addClearListener(ActionListener l) { btnClear.addActionListener(l); }
    public void addTableMouseListener(MouseAdapter a) { table.addMouseListener(a); }
}
