
package tugasbesarpbo.laundry.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LaporanDAO {

    public int getJumlahPesanan() throws SQLException {
        String sql = """
            SELECT COUNT(*) AS total
            FROM pesanan
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        }

        return 0;
    }

    
    public BigDecimal getTotalPendapatan() throws SQLException {
        String sql = """
            SELECT COALESCE(SUM(jumlah_bayar), 0) AS total
            FROM pembayaran
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getBigDecimal("total");
            }
        }

        return BigDecimal.ZERO;
    }

    
    public List<Object[]> getJumlahPesananPerStatus() throws SQLException {
        List<Object[]> data = new ArrayList<>();

        String sql = """
            SELECT status, COUNT(*) AS total
            FROM pesanan
            GROUP BY status
            ORDER BY status
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                data.add(new Object[] {
                    rs.getString("status"),
                    rs.getInt("total")
                });
            }
        }

        return data;
    }
}
