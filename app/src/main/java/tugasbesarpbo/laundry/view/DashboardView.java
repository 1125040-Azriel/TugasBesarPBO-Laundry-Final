
package tugasbesarpbo.laundry.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import tugasbesarpbo.laundry.controller.LaporanController;
import tugasbesarpbo.laundry.controller.LayananController;
import tugasbesarpbo.laundry.controller.LihatPesananController;
import tugasbesarpbo.laundry.controller.LoginController;
import tugasbesarpbo.laundry.controller.PelangganController;
import tugasbesarpbo.laundry.controller.PembayaranController;
import tugasbesarpbo.laundry.controller.StatusLaundryController;
import tugasbesarpbo.laundry.controller.TransaksiController;
import tugasbesarpbo.laundry.model.User;

public class DashboardView extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);
    private final JPanel menuPanel = new JPanel();

    private JPanel currentPage;

    private final JButton backButton = new JButton("Kembali ke Menu");

    public DashboardView(User user) {
        setTitle("Sistem Manajemen Laundry - Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel(
                "Selamat datang, " + user.getNamaRole(),
                SwingConstants.CENTER
        );
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);
        headerPanel.add(backButton, BorderLayout.EAST);

        backButton.setVisible(false);
        backButton.addActionListener(e -> showMenu());

        buatMenu(user);

        contentPanel.add(menuPanel, "MENU");

        setLayout(new BorderLayout(10, 10));
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        cardLayout.show(contentPanel, "MENU");
    }

    private void buatMenu(User user) {

        switch (user.getRole()) {

            case ADMIN:
                JButton layananAdmin = new JButton("Kelola Layanan");
                layananAdmin.addActionListener(e -> {
                    LayananController controller = new LayananController();
                    showPage(controller.getView());
                });
                menuPanel.add(layananAdmin);

                JButton pelangganAdmin = new JButton("Kelola Pelanggan");
                pelangganAdmin.addActionListener(e -> {
                    PelangganController controller = new PelangganController();
                    showPage(controller.getView());
                });
                menuPanel.add(pelangganAdmin);

                JButton laporanAdmin = new JButton("Lihat Laporan");
                laporanAdmin.addActionListener(e -> {
                    LaporanController controller = new LaporanController();
                    showPage(controller.getView());
                });
                menuPanel.add(laporanAdmin);
                break;

            case KASIR:
                JButton transaksiButton = new JButton("Buat Transaksi");
                transaksiButton.addActionListener(e -> {
                    TransaksiController controller = new TransaksiController();
                    showPage(controller.getView());
                });
                menuPanel.add(transaksiButton);

                JButton pembayaranButton = new JButton("Proses Pembayaran");
                pembayaranButton.addActionListener(e -> {
                    PembayaranController controller = new PembayaranController();
                    showPage(controller.getView());
                });
                menuPanel.add(pembayaranButton);

                JButton lihatPesananKasir = new JButton("Lihat Pesanan");
                lihatPesananKasir.addActionListener(e -> {
                    LihatPesananController controller =
                            new LihatPesananController();
                    showPage(controller.getView());
                });
                menuPanel.add(lihatPesananKasir);

                JButton pelangganKasir = new JButton("Kelola Pelanggan");
                pelangganKasir.addActionListener(e -> {
                    PelangganController controller = new PelangganController();
                    showPage(controller.getView());
                });
                menuPanel.add(pelangganKasir);
                break;

            case PETUGAS:
                JButton lihatPesananPetugas = new JButton("Lihat Pesanan");
                lihatPesananPetugas.addActionListener(e -> {
                    LihatPesananController controller =
                            new LihatPesananController();
                    showPage(controller.getView());
                });
                menuPanel.add(lihatPesananPetugas);

                JButton statusLaundryButton =
                        new JButton("Update Status Laundry");
                statusLaundryButton.addActionListener(e -> {
                    StatusLaundryController controller =
                            new StatusLaundryController();
                    showPage(controller.getView());
                });
                menuPanel.add(statusLaundryButton);
                break;
        }

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            LoginController controller = new LoginController();
            controller.start();
        });

        menuPanel.add(logoutButton);
    }

    private void showPage(JPanel page) {
        if (currentPage != null) {
            contentPanel.remove(currentPage);
        }

        currentPage = page;
        contentPanel.add(currentPage, "CONTENT");

        backButton.setVisible(true);
        cardLayout.show(contentPanel, "CONTENT");

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showMenu() {
        if (currentPage != null) {
            contentPanel.remove(currentPage);
            currentPage = null;
        }

        backButton.setVisible(false);
        cardLayout.show(contentPanel, "MENU");

        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
