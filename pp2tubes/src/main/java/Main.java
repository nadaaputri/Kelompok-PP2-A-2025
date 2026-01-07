import javax.swing.SwingUtilities;
import view.mainView;
 public class Main {
    public static void main(String[] args) {
        // Memanggil aplikasi MahasiswaApp untuk dijalankan
        SwingUtilities.invokeLater(() -> {
            new mainView().setVisible(true);
        });
    }
}