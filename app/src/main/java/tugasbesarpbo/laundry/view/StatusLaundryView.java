package tugasbesarpbo.laundry.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class StatusLaundryView extends JPanel {

    private final DefaultTableModel model;
    private final JTable tabel;
    private final JComboBox<String> statusCombo;
    private final JLabel pesanLabel;
    private final JButton ubahButton;
    private final JButton muatButton;

    public StatusLaundryView() {
        setLayout(new BorderLayout(10, 10));

        JLabel judul = new JLabel(
                "Update Status Laundry",
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

        tabel = new JTable(model);
        add(judul, BorderLayout.NORTH);
        add(new JScrollPane(tabel), BorderLayout.CENTER);

        statusCombo = new JComboBox<>(
                new String[]{"MENUNGGU", "DIPROSES", "SELESAI"}
        );

        ubahButton = new JButton("Ubah Status");
        muatButton = new JButton("Muat Ulang");
        pesanLabel = new JLabel("Pilih pesanan terlebih dahulu.");

        JPanel panelKontrol = new JPanel(new FlowLayout());
        panelKontrol.add(new JLabel("Status Baru:"));
        panelKontrol.add(statusCombo);
        panelKontrol.add(ubahButton);
        panelKontrol.add(muatButton);

        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.add(panelKontrol, BorderLayout.CENTER);
        panelBawah.add(pesanLabel, BorderLayout.SOUTH);

        add(panelBawah, BorderLayout.SOUTH);
    }

    public int getIdPesananTerpilih() {
        int baris = tabel.getSelectedRow();

        if (baris < 0) {
            throw new IllegalStateException("Pilih pesanan terlebih dahulu.");
        }

        return Integer.parseInt(
                model.getValueAt(baris, 0).toString()
        );
    }

    public String getStatusTerpilih() {
        return statusCombo.getSelectedItem().toString();
    }

    public void setDataPesanan(List<Object[]> data) {
        model.setRowCount(0);

        for (Object[] baris : data) {
            model.addRow(baris);
        }
    }

    public void setUbahAction(java.awt.event.ActionListener action) {
        ubahButton.addActionListener(action);
    }

    public void setMuatAction(java.awt.event.ActionListener action) {
        muatButton.addActionListener(action);
    }

    public void setPesan(String pesan) {
        pesanLabel.setText(pesan);
    }
}