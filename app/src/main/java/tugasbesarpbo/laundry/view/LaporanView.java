package tugasbesarpbo.laundry.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class LaporanView extends JPanel {

    private final JLabel statusLabel;

    public LaporanView(
            int jumlahPesanan,
            BigDecimal totalPendapatan,
            List<Object[]> dataStatus) {

        NumberFormat formatRupiah =
                NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        JLabel judul = new JLabel(
                "LAPORAN SISTEM LAUNDRY",
                SwingConstants.CENTER
        );
        judul.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel infoPanel = new JPanel(new BorderLayout());

        JLabel jumlahLabel = new JLabel(
                "Jumlah Seluruh Pesanan: " + jumlahPesanan,
                SwingConstants.CENTER
        );
        jumlahLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel pendapatanLabel = new JLabel(
                "Total Pendapatan: " +
                formatRupiah.format(totalPendapatan),
                SwingConstants.CENTER
        );
        pendapatanLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel ringkasanPanel = new JPanel(
                new GridLayout(2, 1, 5, 5)
        );
        ringkasanPanel.add(jumlahLabel);
        ringkasanPanel.add(pendapatanLabel);

        infoPanel.add(judul, BorderLayout.NORTH);
        infoPanel.add(ringkasanPanel, BorderLayout.CENTER);

        String[] kolom = {
            "Status Pesanan",
            "Jumlah Pesanan"
        };

        DefaultTableModel model = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Object[] data : dataStatus) {
            model.addRow(data);
        }

        JTable tabelLaporan = new JTable(model);
        tabelLaporan.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tabelLaporan);

        statusLabel = new JLabel(
                "Laporan berhasil dimuat.",
                SwingConstants.CENTER
        );

        setLayout(new BorderLayout(10, 15));

        add(infoPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    public void setStatus(String pesan) {
        statusLabel.setText(pesan);
    }
}