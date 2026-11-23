package tugasbesarpbo.laundry.view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class LihatPesananView extends JPanel {

    private final DefaultTableModel model;
    private final JLabel pesanLabel;

    public LihatPesananView() {
        setLayout(new BorderLayout(10, 10));

        JLabel judul = new JLabel(
                "Daftar Pesanan Laundry",
                SwingConstants.CENTER
        );

        model = new DefaultTableModel(
                new String[]{
                    "ID Pesanan", "Pelanggan", "Layanan",
                    "Berat (Kg)", "Total Harga", "Status"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabel = new JTable(model);
        pesanLabel = new JLabel(
                "Daftar pesanan berhasil dimuat.",
                SwingConstants.CENTER
        );

        add(judul, BorderLayout.NORTH);
        add(new JScrollPane(tabel), BorderLayout.CENTER);
        add(pesanLabel, BorderLayout.SOUTH);
    }

    public void setDataPesanan(List<Object[]> data) {
        model.setRowCount(0);

        for (Object[] baris : data) {
            model.addRow(baris);
        }

        pesanLabel.setText(
                "Jumlah pesanan: " + data.size()
        );
    }

    public void setPesan(String pesan) {
        pesanLabel.setText(pesan);
    }
}