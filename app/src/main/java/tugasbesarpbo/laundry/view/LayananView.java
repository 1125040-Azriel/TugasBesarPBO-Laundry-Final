
package tugasbesarpbo.laundry.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class LayananView extends JPanel {

    private final JTextField idField = new JTextField();
    private final JTextField namaField = new JTextField();
    private final JTextField hargaField = new JTextField();

    private final DefaultTableModel tableModel =
            new DefaultTableModel(
                    new String[]{"ID", "Nama Layanan", "Harga per Kg"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    private final JTable table = new JTable(tableModel);
    private final JLabel statusLabel =
            new JLabel("Siap.", SwingConstants.CENTER);

    private final JButton tambahButton = new JButton("Tambah");
    private final JButton ubahButton = new JButton("Ubah");
    private final JButton hapusButton = new JButton("Hapus");
    private final JButton muatButton = new JButton("Muat Ulang");
    private final JButton bersihkanButton = new JButton("Bersihkan");

    public LayananView() {
        setLayout(new BorderLayout(8, 8));

        idField.setEditable(false);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        formPanel.add(new JLabel("ID Layanan"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Nama Layanan"));
        formPanel.add(namaField);
        formPanel.add(new JLabel("Harga per Kg"));
        formPanel.add(hargaField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(tambahButton);
        buttonPanel.add(ubahButton);
        buttonPanel.add(hapusButton);
        buttonPanel.add(muatButton);
        buttonPanel.add(bersihkanButton);

        JPanel topPanel = new JPanel(new BorderLayout(8, 8));
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();

                idField.setText(
                        String.valueOf(tableModel.getValueAt(row, 0)));
                namaField.setText(
                        String.valueOf(tableModel.getValueAt(row, 1)));
                hargaField.setText(
                        String.valueOf(tableModel.getValueAt(row, 2)));
            }
        });
    }

    public String getId() {
        return idField.getText().trim();
    }

    public String getNama() {
        return namaField.getText().trim();
    }

    public String getHarga() {
        return hargaField.getText().trim();
    }

    public void setStatus(String pesan) {
        statusLabel.setText(pesan);
    }

    public void setTambahAction(Runnable action) {
        tambahButton.addActionListener(e -> action.run());
    }

    public void setUbahAction(Runnable action) {
        ubahButton.addActionListener(e -> action.run());
    }

    public void setHapusAction(Runnable action) {
        hapusButton.addActionListener(e -> action.run());
    }

    public void setMuatAction(Runnable action) {
        muatButton.addActionListener(e -> action.run());
    }

    public void setBersihkanAction(Runnable action) {
        bersihkanButton.addActionListener(e -> action.run());
    }

    public void setLayananData(Object[][] data) {
        tableModel.setRowCount(0);

        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    public void clearForm() {
        idField.setText("");
        namaField.setText("");
        hargaField.setText("");
        table.clearSelection();
    }
}
