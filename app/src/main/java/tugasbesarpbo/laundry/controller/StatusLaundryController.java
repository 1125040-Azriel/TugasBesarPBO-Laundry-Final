package tugasbesarpbo.laundry.controller;

import java.sql.SQLException;
import java.util.List;

import tugasbesarpbo.laundry.dao.TransaksiDAO;
import tugasbesarpbo.laundry.view.StatusLaundryView;

public class StatusLaundryController {

    private final TransaksiDAO dao;
    private final StatusLaundryView view;

    public StatusLaundryController() {
        dao = new TransaksiDAO();
        view = new StatusLaundryView();

        view.setUbahAction(e -> ubahStatus());
        view.setMuatAction(e -> muatPesanan());

        muatPesanan();
    }

    private void muatPesanan() {
        try {
            List<Object[]> data = dao.getPesanan();
            view.setDataPesanan(data);
            view.setPesan("Data pesanan berhasil dimuat.");
        } catch (SQLException ex) {
            view.setPesan("Gagal memuat pesanan: " + ex.getMessage());
        }
    }

    private void ubahStatus() {
        try {
            int idPesanan = view.getIdPesananTerpilih();
            String status = view.getStatusTerpilih();

            dao.updateStatus(idPesanan, status);

            view.setPesan("Status pesanan berhasil diperbarui.");
            muatPesanan();

        } catch (IllegalStateException ex) {
            view.setPesan(ex.getMessage());
        } catch (SQLException ex) {
            view.setPesan("Gagal mengubah status: " + ex.getMessage());
        }
    }

    public StatusLaundryView getView() {
        return view;
    }
}