package tugasbesarpbo.laundry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LayananDAO implements Repository<Object[]> {

    @Override
    public List<Object[]> getAll() throws SQLException {
        List<Object[]> daftarLayanan = new ArrayList<>();

        String sql = """
            SELECT id_layanan, nama_layanan, harga_per_kg
            FROM layanan
            ORDER BY id_layanan
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                daftarLayanan.add(new Object[] {
                    result.getInt("id_layanan"),
                    result.getString("nama_layanan"),
                    result.getDouble("harga_per_kg")
                });
            }
        }

        return daftarLayanan;
    }

    public void insert(String nama, double harga)
            throws SQLException {

        String sql = """
            INSERT INTO layanan (nama_layanan, harga_per_kg)
            VALUES (?, ?)
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, nama);
            statement.setDouble(2, harga);
            statement.executeUpdate();
        }
    }

    public boolean update(int id, String nama, double harga)
            throws SQLException {

        String sql = """
            UPDATE layanan
            SET nama_layanan = ?, harga_per_kg = ?
            WHERE id_layanan = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, nama);
            statement.setDouble(2, harga);
            statement.setInt(3, id);

            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = """
            DELETE FROM layanan
            WHERE id_layanan = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }
}