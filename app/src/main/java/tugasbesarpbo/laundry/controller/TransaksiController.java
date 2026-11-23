package tugasbesarpbo.laundry.controller;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import tugasbesarpbo.laundry.dao.TransaksiDAO;
import tugasbesarpbo.laundry.model.LaundryException;
import tugasbesarpbo.laundry.view.TransaksiView;

public class TransaksiController {

    private final TransaksiView view;
    private final TransaksiDAO dao;

    public TransaksiController() {
        view = new TransaksiView();
        dao = new TransaksiDAO();

        view.setHitungAction(this::hitungTotal);
        view.setSimpanAction(this::simpanPesanan);
        view.setMuatAction(e -> muatSemuaData());
        view.setLayananSelectionAction(e -> perbaruiHargaLayanan());

        muatSemuaData();
        perbaruiHargaLayanan();
    }

    private void hitungTotal(ActionEvent e) {
        try {
            double berat = bacaBerat();

            double hargaPerKg = view.getHargaPerKg();
            double total = BigDecimal.valueOf(berat)
                    .multiply(BigDecimal.valueOf(hargaPerKg))
                    .setScale(2, java.math.RoundingMode.HALF_UP)
                    .doubleValue();

            view.setHarga(hargaPerKg);
            view.setTotal(total);
            view.setStatus("Total harga berhasil dihitung.");

        } catch (LaundryException ex) {
            view.setStatus(ex.getMessage());
        } catch (IllegalStateException ex) {
            view.setStatus(ex.getMessage());
        }
    }

    private void simpanPesanan(ActionEvent e) {
        try {
            int idPelanggan = view.getIdPelanggan();
            int idLayanan = view.getIdLayanan();
            double berat = bacaBerat();
            double hargaPerKg = view.getHargaPerKg();

            double total = BigDecimal.valueOf(berat)
                    .multiply(BigDecimal.valueOf(hargaPerKg))
                    .setScale(2, java.math.RoundingMode.HALF_UP)
                    .doubleValue();

            dao.buatPesanan(
                    idPelanggan,
                    idLayanan,
                    berat,
                    total
            );

            view.setHarga(hargaPerKg);
            view.setTotal(total);
            view.setStatus("Pesanan berhasil disimpan.");
            view.clearForm();
            muatPesanan();

        } catch (LaundryException ex) {
            view.setStatus(ex.getMessage());
        } catch (IllegalStateException ex) {
            view.setStatus(ex.getMessage());
        } catch (SQLException ex) {
            view.setStatus("Gagal menyimpan pesanan ke database.");
            System.err.println("Kesalahan database: " + ex.getMessage());
        }
    }

    private double bacaBerat() throws LaundryException {
        String teks = view.getBerat();

        if (teks.isEmpty()) {
            throw new LaundryException("Berat belum diisi.");
        }

        try {
            double berat = Double.parseDouble(teks);

            if (!Double.isFinite(berat) || berat <= 0) {
                throw new LaundryException("Berat harus lebih dari nol.");
            }

            return berat;
        } catch (NumberFormatException ex) {
            throw new LaundryException("Berat harus berupa angka yang valid.");
        }
    }

    private void perbaruiHargaLayanan() {
        try {
            double harga = view.getHargaPerKg();
            view.setHarga(harga);
        } catch (IllegalStateException ex) {
            view.setHarga(0);
        }
    }

    private void muatSemuaData() {
        try {
            List<Object[]> pelanggan = dao.getPelanggan();
            view.setPelangganData(pelanggan.toArray(new Object[0][]));

            List<Object[]> layanan = dao.getLayanan();
            view.setLayananData(layanan.toArray(new Object[0][]));

            muatPesanan();

        } catch (SQLException ex) {
            view.setStatus("Gagal memuat data dari database.");
            System.err.println("Kesalahan database: " + ex.getMessage());
        }
    }

    private void muatPesanan() {
        try {
            List<Object[]> pesanan = dao.getPesanan();
            view.setPesananData(pesanan.toArray(new Object[0][]));
        } catch (SQLException ex) {
            view.setStatus("Gagal memuat daftar pesanan.");
            System.err.println("Kesalahan database: " + ex.getMessage());
        }
    }

    public TransaksiView getView() {
        return view;
    }
}