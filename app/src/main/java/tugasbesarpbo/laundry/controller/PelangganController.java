
package tugasbesarpbo.laundry.controller;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JPanel;

import tugasbesarpbo.laundry.dao.PelangganDAO;
import tugasbesarpbo.laundry.model.Customer;
import tugasbesarpbo.laundry.view.PelangganView;

public class PelangganController {

    private final PelangganView view;
    private final PelangganDAO dao;

    public PelangganController() {
        view = new PelangganView();
        dao = new PelangganDAO();

        view.setTambahAction(this::tambahPelanggan);
        view.setUbahAction(this::ubahPelanggan);
        view.setHapusAction(this::hapusPelanggan);
        view.setMuatAction(this::muatPelanggan);
        view.setBersihkanAction(view::clearForm);

        muatPelanggan();
    }

    private void tambahPelanggan() {
        String nama = view.getNama();
        String telepon = view.getTelepon();

        if (nama.isEmpty()) {
            view.setStatus("Nama pelanggan wajib diisi.");
            return;
        }

        try {
            dao.insert(nama, telepon);
            view.setStatus("Pelanggan berhasil ditambahkan.");
            view.clearForm();
            muatPelanggan();

        } catch (SQLException e) {
            view.setStatus("Gagal menambahkan pelanggan.");
            System.err.println("Kesalahan database: " + e.getMessage());
        }
    }

    private void ubahPelanggan() {
        String id = view.getId();
        String nama = view.getNama();
        String telepon = view.getTelepon();

        if (id.isEmpty()) {
            view.setStatus("Pilih pelanggan yang ingin diubah.");
            return;
        }

        if (nama.isEmpty()) {
            view.setStatus("Nama pelanggan wajib diisi.");
            return;
        }

        try {
            boolean berhasil = dao.update(id, nama, telepon);

            if (berhasil) {
                view.setStatus("Data pelanggan berhasil diubah.");
                view.clearForm();
                muatPelanggan();
            } else {
                view.setStatus("Data pelanggan tidak ditemukan.");
            }

        } catch (SQLException | NumberFormatException e) {
            view.setStatus("Gagal mengubah data pelanggan.");
            System.err.println("Kesalahan: " + e.getMessage());
        }
    }

    private void hapusPelanggan() {
        String id = view.getId();

        if (id.isEmpty()) {
            view.setStatus("Pilih pelanggan yang ingin dihapus.");
            return;
        }

        try {
            boolean berhasil = dao.delete(id);

            if (berhasil) {
                view.setStatus("Pelanggan berhasil dihapus.");
                view.clearForm();
                muatPelanggan();
            } else {
                view.setStatus("Data pelanggan tidak ditemukan.");
            }

        } catch (SQLException | NumberFormatException e) {
            view.setStatus(
                    "Gagal menghapus. Periksa apakah pelanggan memiliki pesanan."
            );
            System.err.println("Kesalahan: " + e.getMessage());
        }
    }

    private void muatPelanggan() {
        try {
            List<Customer> daftar = dao.getAll();
            Object[][] data = new Object[daftar.size()][3];

            for (int i = 0; i < daftar.size(); i++) {
                Customer customer = daftar.get(i);

                data[i][0] = customer.getId();
                data[i][1] = customer.getNama();
                data[i][2] = customer.getNomorTelepon();
            }

            view.setPelangganData(data);
            view.setStatus("Jumlah pelanggan: " + daftar.size());

        } catch (SQLException e) {
            view.setStatus("Gagal memuat data pelanggan.");
            System.err.println("Kesalahan database: " + e.getMessage());
        }
    }

    public JPanel getView() {
        return view;
    }
}
