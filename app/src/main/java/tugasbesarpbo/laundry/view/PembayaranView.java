package tugasbesarpbo.laundry.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PembayaranView extends JPanel {

    private final JTable tabel;
    private final DefaultTableModel model;
    private final JComboBox<String> metodeCombo;
    private final JLabel totalLabel;
    private final JLabel statusLabel;
    private final JButton bayarButton;
    private int idPesananTerpilih = -1;
    private double totalTerpilih = 0;

    public PembayaranView() {
        setLayout(new BorderLayout(10, 10));

        model = new DefaultTableModel(
            new String[]{"ID Pesanan", "Pelanggan", "Layanan",
                         "Berat (Kg)", "Total Harga", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabel = new JTable(model);
        add(new JScrollPane(tabel), BorderLayout.CENTER);

        metodeCombo = new JComboBox<>(
            new String[]{"TUNAI", "TRANSFER", "QRIS"}
        );
        totalLabel = new JLabel("Total pembayaran: Rp0");
        statusLabel = new JLabel("Pilih pesanan yang akan dibayar.");
        bayarButton = new JButton("Proses Pembayaran");

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        formPanel.add(new JLabel("Metode Pembayaran"));
        formPanel.add(metodeCombo);
        formPanel.add(new JLabel("Total"));
        formPanel.add(totalLabel);

        JButton muatButton = new JButton("Muat Ulang");
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(bayarButton);
        buttonPanel.add(muatButton);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.add(formPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        tabel.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tabel.getSelectedRow();

                if (row >= 0) {
                    idPesananTerpilih =
                        Integer.parseInt(model.getValueAt(row, 0).toString());
                    totalTerpilih =
                        Double.parseDouble(model.getValueAt(row, 4).toString());
                    totalLabel.setText(
                        String.format("Total pembayaran: Rp%,.0f", totalTerpilih)
                    );
                }
            }
        });

        muatButton.addActionListener(e -> {
            if (muatAction != null) {
                muatAction.actionPerformed(e);
            }
        });
    }

    private ActionListener muatAction;

    public int getIdPesananTerpilih() {
        if (idPesananTerpilih < 0) {
            throw new IllegalStateException("Pilih pesanan terlebih dahulu.");
        }
        return idPesananTerpilih;
    }

    public String getMetodePembayaran() {
        return metodeCombo.getSelectedItem().toString();
    }

    public void setBayarAction(ActionListener action) {
        bayarButton.addActionListener(action);
    }

    public void setMuatAction(ActionListener action) {
        muatAction = action;
    }

    public void setPesananData(List<Object[]> data) {
        model.setRowCount(0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        idPesananTerpilih = -1;
        totalTerpilih = 0;
        totalLabel.setText("Total pembayaran: Rp0");
    }

    public void setStatus(String pesan) {
        statusLabel.setText(pesan);
    }
}