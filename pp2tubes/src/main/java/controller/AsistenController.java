package controller;

import view.mainView;

public class AsistenController {
    private mainView view;

    public AsistenController(mainView view) {
        this.view = view;
    }

    // Logika untuk mencari data asisten
    public void search() {
        String keyword = view.txtCari.getText();
        System.out.println("Mencari asisten dengan kata kunci: " + keyword);
        
        // TODO: Tambahkan logika filter tabel berdasarkan keyword
        // Contoh: model.getDataVector().removeAllElements();
    }

    // Logika pendukung lainnya (Insert, Update, Delete)
    public void insert() {
        String nama = view.txtNama.getText();
        // Implementasi simpan data ke database/list
    }
}