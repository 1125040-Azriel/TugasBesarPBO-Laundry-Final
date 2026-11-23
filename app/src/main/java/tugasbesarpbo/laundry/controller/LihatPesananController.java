package tugasbesarpbo.laundry.controller;

import java.sql.SQLException;

import tugasbesarpbo.laundry.dao.TransaksiDAO;
import tugasbesarpbo.laundry.view.LihatPesananView;

public class LihatPesananController {

    private final LihatPesananView view;
    private final TransaksiDAO dao;

    public LihatPesananController() {
        view = new LihatPesananView();
        dao = new TransaksiDAO();
        muatPesanan();
    }

    private void muatPesanan() {
        try {
            view.setDataPesanan(dao.getPesanan());
        } catch (SQLException e) {
            view.setPesan(
                    "Gagal memuat pesanan: " + e.getMessage()
            );
        }
    }

    public LihatPesananView getView() {
        return view;
    }
}