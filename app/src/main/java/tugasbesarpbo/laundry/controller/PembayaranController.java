package tugasbesarpbo.laundry.controller;

import java.sql.SQLException;
import java.util.List;

import tugasbesarpbo.laundry.dao.PembayaranDAO;
import tugasbesarpbo.laundry.view.PembayaranView;

public class PembayaranController {

    private final PembayaranDAO dao;
    private final PembayaranView view;

    public PembayaranController() {
        dao = new PembayaranDAO();
        view = new PembayaranView();

        view.setBayarAction(e -> prosesPembayaran());
        view.setMuatAction(e -> muatPesanan());

        muatPesanan();
    }

    private void muatPesanan() {
        try {
            List<Object[]> data = dao.getPesananBelumDibayar();
            view.setPesananData(data);

            if (data.isEmpty()) {
                view.setStatus("Tidak ada pesanan yang menunggu pembayaran.");
            } else {
                view.setStatus("Pilih pesanan yang akan dibayar.");
            }
        } catch (SQLException ex) {
            view.setStatus("Gagal memuat pesanan: " + ex.getMessage());
        }
    }

    private void prosesPembayaran() {
        try {
            int idPesanan = view.getIdPesananTerpilih();
            String metode = view.getMetodePembayaran();

            dao.prosesPembayaran(idPesanan, 0, metode);

            view.setStatus("Pembayaran berhasil disimpan.");
            muatPesanan();

        } catch (IllegalStateException ex) {
            view.setStatus(ex.getMessage());
        } catch (SQLException ex) {
            view.setStatus("Gagal memproses pembayaran: " + ex.getMessage());
        }
    }

    public PembayaranView getView() {
        return view;
    }
}