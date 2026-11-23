package tugasbesarpbo.laundry.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class TransaksiView extends JPanel {

    private final JComboBox<Object[]> pelangganCombo = new JComboBox<>();
    private final JComboBox<Object[]> layananCombo = new JComboBox<>();
    private final JTextField beratField = new JTextField();

    private final JLabel hargaLabel = new JLabel("Harga per Kg: Rp0");
    private final JLabel totalLabel = new JLabel("Total: Rp0");
    private final JLabel statusLabel =
            new JLabel("Siap.", SwingConstants.CENTER);

    private final DefaultTableModel tableModel =
            new DefaultTableModel(
                    new String[]{
                        "ID Pesanan", "Pelanggan", "Layanan",
                        "Berat (Kg)", "Total Harga", "Status"
                    }, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    private final JTable table = new JTable(tableModel);

    private final JButton hitungButton = new JButton("Hitung Total");
    private final JButton simpanButton = new JButton("Simpan Pesanan");
    private final JButton muatButton = new JButton("Muat Ulang");

    public TransaksiView() {
        setLayout(new BorderLayout(8, 8));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));

        formPanel.add(new JLabel("Pelanggan"));
        formPanel.add(pelangganCombo);

        formPanel.add(new JLabel("Layanan"));
        formPanel.add(layananCombo);

        formPanel.add(new JLabel("Berat (Kg)"));
        formPanel.add(beratField);

        formPanel.add(new JLabel("Harga Layanan"));
        formPanel.add(hargaLabel);

        formPanel.add(new JLabel("Total Harga"));
        formPanel.add(totalLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitungButton);
        buttonPanel.add(simpanButton);
        buttonPanel.add(muatButton);

        JPanel topPanel = new JPanel(new BorderLayout(8, 8));
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    public void setPelangganData(Object[][] data) {
        pelangganCombo.removeAllItems();

        for (Object[] row : data) {
            pelangganCombo.addItem(new Object[]{row[0], row[1]});
        }

        pelangganCombo.setRenderer((list, value, index, selected, focus) -> {
            JLabel label = new JLabel(
                    value == null ? "" : value[1].toString()
            );

            if (selected) {
                label.setOpaque(true);
                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
            }

            return label;
        });
    }

    public void setLayananData(Object[][] data) {
        layananCombo.removeAllItems();

        for (Object[] row : data) {
            layananCombo.addItem(
                    new Object[]{row[0], row[1], row[2]}
            );
        }

        layananCombo.setRenderer((list, value, index, selected, focus) -> {
            JLabel label = new JLabel(
                    value == null ? "" : value[1].toString()
            );

            if (selected) {
                label.setOpaque(true);
                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
            }

            return label;
        });
    }

    public int getIdPelanggan() {
        Object[] item = (Object[]) pelangganCombo.getSelectedItem();
        if (item == null) {
            throw new IllegalStateException("Pilih pelanggan terlebih dahulu.");
        }
        return Integer.parseInt(item[0].toString());
    }

    public int getIdLayanan() {
        Object[] item = (Object[]) layananCombo.getSelectedItem();
        if (item == null) {
            throw new IllegalStateException("Pilih layanan terlebih dahulu.");
        }
        return Integer.parseInt(item[0].toString());
    }

    public double getHargaPerKg() {
        Object[] item = (Object[]) layananCombo.getSelectedItem();
        if (item == null) {
            throw new IllegalStateException("Pilih layanan terlebih dahulu.");
        }
        return Double.parseDouble(item[2].toString());
    }

    public String getBerat() {
        return beratField.getText().trim();
    }

    public void setHarga(double harga) {
        hargaLabel.setText("Harga per Kg: Rp" + formatRupiah(harga));
    }

    public void setTotal(double total) {
        totalLabel.setText("Total: Rp" + formatRupiah(total));
    }

    private String formatRupiah(double nilai) {
        return String.format("%,.2f", nilai);
    }

    public void setPesananData(Object[][] data) {
        tableModel.setRowCount(0);

        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    public void setStatus(String pesan) {
        statusLabel.setText(pesan);
    }

    public void setHitungAction(ActionListener action) {
        hitungButton.addActionListener(action);
    }

    public void setSimpanAction(ActionListener action) {
        simpanButton.addActionListener(action);
    }

    public void setMuatAction(ActionListener action) {
        muatButton.addActionListener(action);
    }

    public void setLayananSelectionAction(ActionListener action) {
        layananCombo.addActionListener(action);
    }

    public void clearForm() {
        beratField.setText("");
        setHarga(0);
        setTotal(0);
    }
}