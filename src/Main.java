package src;

import src.main.java.controller.AsistenController;
import src.main.java.controller.JadwalController;
import src.main.java.controller.PraktikumController;
import src.main.java.view.AsistenView;
import src.main.java.view.JadwalView;
import src.main.java.view.PraktikumView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            JFrame frame = new JFrame("Sistem Manajemen Jadwal Praktikum");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null);

            JTabbedPane tabs = new JTabbedPane();


            JadwalView jadwalView = new JadwalView();
            JadwalController jadwalController = new JadwalController(jadwalView);
            jadwalView.setController(jadwalController);
            tabs.addTab("Jadwal Praktikum", jadwalView);

            AsistenView asistenView = new AsistenView();
            new AsistenController(asistenView, jadwalView);
            tabs.addTab("Kelola Asisten", asistenView);


            PraktikumView praktikumView = new PraktikumView();
            new PraktikumController(praktikumView, jadwalView);
            tabs.addTab("Kelola Praktikum", praktikumView);



            frame.add(tabs);
            frame.setVisible(true);
        });



    }
}
