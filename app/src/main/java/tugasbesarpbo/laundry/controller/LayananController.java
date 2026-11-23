
package tugasbesarpbo.laundry.controller;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JPanel;

import tugasbesarpbo.laundry.dao.LayananDAO;
import tugasbesarpbo.laundry.view.LayananView;

public class LayananController {

    private final LayananView view;
    private final LayananDAO dao;

    public LayananController() {
        view = new LayananView();
        dao = new LayananDAO();

        view.setTambahAction(this::tambahLayanan);
        view.setUbahAction(this::ubahLayanan);
        view.setHapusAction(this::hapusLayanan);
        view.setMuatAction(this::muatLayanan);
        view.setBersihkanAction(view::clearForm);

        muatLayanan();
    }

    private void tambahLayanan() {
        String nama = view.getNama();
        String hargaText = view.getHarga();

        if (nama.isEmpty() || hargaText.isEmpty()) {
            view.setStatus("Nama dan harga layanan wajib diisi.");
            return;
        }

        try {
            double harga = Double.parseDouble(hargaText);

            if (!Double.isFinite(harga) || harga <= 0) {
                view.setStatus("Harga harus berupa angka lebih dari 0.");
                return;
            }

            dao.insert(nama, harga);
            view.setStatus("Layanan berhasil ditambahkan.");
            view.clearForm();
            muatLayanan();

        } catch (NumberFormatException e) {
            view.setStatus("Harga harus berupa angka yang valid.");
        } catch (SQLException e) {
            view.setStatus("Gagal menambahkan layanan.");
            System.err.println("Kesalahan database: " + e.getMessage());
        }
    }

    private void ubahLayanan() {
        String idText = view.getId();
        String nama = view.getNama();
        String hargaText = view.getHarga();

        if (idText.isEmpty()) {
            view.setStatus("Pilih layanan yang ingin diubah.");
            return;
        }

        if (nama.isEmpty() || hargaText.isEmpty()) {
            view.setStatus("Nama dan harga layanan wajib diisi.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            double harga = Double.parseDouble(hargaText);

            if (!Double.isFinite(harga) || harga <= 0) {
                view.setStatus("Harga harus berupa angka lebih dari 0.");
                return;
            }

            boolean berhasil = dao.update(id, nama, harga);

            if (berhasil) {
                view.setStatus("Layanan berhasil diubah.");
                view.clearForm();
            } else {
                view.setStatus("Layanan tidak ditemukan.");
            }

            muatLayanan();

        } catch (NumberFormatException e) {
            view.setStatus("ID atau harga tidak valid.");
        } catch (SQLException e) {
            view.setStatus("Gagal mengubah layanan.");
            System.err.println("Kesalahan database: " + e.getMessage());
        }
    }

    private void hapusLayanan() {
        String idText = view.getId();

        if (idText.isEmpty()) {
            view.setStatus("Pilih layanan yang ingin dihapus.");
            return;
        }

        try {
            boolean berhasil = dao.delete(Integer.parseInt(idText));

            if (berhasil) {
                view.setStatus("Layanan berhasil dihapus.");
                view.clearForm();
            } else {
                view.setStatus("Layanan tidak ditemukan.");
            }

            muatLayanan();

        } catch (NumberFormatException e) {
            view.setStatus("ID layanan tidak valid.");
        } catch (SQLException e) {
            view.setStatus(
                    "Gagal menghapus layanan. Periksa apakah layanan digunakan pesanan."
            );
            System.err.println("Kesalahan database: " + e.getMessage());
        }
    }

    private void muatLayanan() {
        try {
            List<Object[]> daftar = dao.getAll();
            Object[][] data = daftar.toArray(new Object[0][]);

            view.setLayananData(data);
            view.setStatus("Jumlah layanan: " + daftar.size());

        } catch (SQLException e) {
            view.setStatus("Gagal memuat data layanan.");
            System.err.println("Kesalahan database: " + e.getMessage());
        }
    }

    public JPanel getView() {
        return view;
    }
}
