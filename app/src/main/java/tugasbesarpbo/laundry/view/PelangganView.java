
package tugasbesarpbo.laundry.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class PelangganView extends JPanel {

    private final JTextField idField = new JTextField();
    private final JTextField namaField = new JTextField();
    private final JTextField teleponField = new JTextField();

    private final DefaultTableModel tableModel =
            new DefaultTableModel(
                    new String[]{"ID", "Nama", "Nomor Telepon"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    private final JTable tabelPelanggan = new JTable(tableModel);
    private final JLabel statusLabel = new JLabel("Siap.");

    private final JButton tambahButton = new JButton("Tambah");
    private final JButton ubahButton = new JButton("Ubah");
    private final JButton hapusButton = new JButton("Hapus");
    private final JButton muatButton = new JButton("Muat Ulang");
    private final JButton bersihkanButton = new JButton("Bersihkan");

    public PelangganView() {
        setLayout(new BorderLayout(8, 8));

        idField.setEditable(false);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        formPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("ID Pelanggan:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Nama:"));
        formPanel.add(namaField);
        formPanel.add(new JLabel("Nomor Telepon:"));
        formPanel.add(teleponField);

        tabelPelanggan.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);

        tabelPelanggan.getSelectionModel()
                .addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) {
                        int row = tabelPelanggan.getSelectedRow();

                        if (row >= 0) {
                            idField.setText(
                                    tableModel.getValueAt(row, 0).toString());
                            namaField.setText(
                                    tableModel.getValueAt(row, 1).toString());

                            Object telepon =
                                    tableModel.getValueAt(row, 2);

                            teleponField.setText(
                                    telepon == null ? "" : telepon.toString());
                        }
                    }
                });

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(tambahButton);
        buttonPanel.add(ubahButton);
        buttonPanel.add(hapusButton);
        buttonPanel.add(muatButton);
        buttonPanel.add(bersihkanButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(tabelPelanggan), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public String getId() {
        return idField.getText().trim();
    }

    public String getNama() {
        return namaField.getText().trim();
    }

    public String getTelepon() {
        return teleponField.getText().trim();
    }

    public void setStatus(String message) {
        statusLabel.setText(message);
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

    public void clearForm() {
        idField.setText("");
        namaField.setText("");
        teleponField.setText("");
        tabelPelanggan.clearSelection();
    }

    public void setPelangganData(Object[][] data) {
        tableModel.setRowCount(0);

        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
}
