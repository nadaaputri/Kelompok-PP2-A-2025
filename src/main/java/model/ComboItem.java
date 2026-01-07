package src.main.java.model;

public class ComboItem {
    private int id;
    private String label;

    public ComboItem(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() { return id; }
    public String getLabel() { return label; }

    @Override
    public String toString() {
        return label; // Ini yang akan muncul di tampilan ComboBox
    }
}