package tugasbesarpbo.laundry.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tugasbesarpbo.laundry.dao.LaporanDAO;
import tugasbesarpbo.laundry.view.LaporanView;

public class LaporanController {

    private final LaporanDAO laporanDAO;
    private final LaporanView view;

    public LaporanController() {
        laporanDAO = new LaporanDAO();

        int jumlahPesanan = 0;
        BigDecimal totalPendapatan = BigDecimal.ZERO;
        List<Object[]> dataStatus = new ArrayList<>();
        String pesanError = null;

        try {
            jumlahPesanan = laporanDAO.getJumlahPesanan();
            totalPendapatan = laporanDAO.getTotalPendapatan();
            dataStatus = laporanDAO.getJumlahPesananPerStatus();
        } catch (SQLException e) {
            pesanError = "Gagal mengambil data laporan: " + e.getMessage();
        }

        view = new LaporanView(
                jumlahPesanan,
                totalPendapatan,
                dataStatus
        );

        if (pesanError != null) {
            view.setStatus(pesanError);
        }
    }

    public LaporanView getView() {
        return view;
    }
}