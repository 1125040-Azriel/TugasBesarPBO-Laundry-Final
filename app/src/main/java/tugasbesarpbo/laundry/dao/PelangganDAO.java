
package tugasbesarpbo.laundry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tugasbesarpbo.laundry.model.Customer;

public class PelangganDAO {

    
    public List<Customer> getAll() throws SQLException {
        List<Customer> daftarPelanggan = new ArrayList<>();

        String sql = """
                SELECT id_pelanggan, nama, nomor_telepon
                FROM pelanggan
                ORDER BY id_pelanggan
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                Customer customer = new Customer(
                        String.valueOf(
                                result.getInt("id_pelanggan")),
                        result.getString("nama"),
                        result.getString("nomor_telepon")
                );

                daftarPelanggan.add(customer);
            }
        }

        return daftarPelanggan;
    }

    // Menambahkan pelanggan
    public void insert(String nama, String nomorTelepon)
            throws SQLException {

        String sql = """
                INSERT INTO pelanggan (nama, nomor_telepon)
                VALUES (?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, nama);
            statement.setString(2, nomorTelepon);
            statement.executeUpdate();
        }
    }

    // Mengubah data pelanggan
    public boolean update(
            String id, String nama, String nomorTelepon)
            throws SQLException {

        String sql = """
                UPDATE pelanggan
                SET nama = ?, nomor_telepon = ?
                WHERE id_pelanggan = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, nama);
            statement.setString(2, nomorTelepon);
            statement.setInt(3, Integer.parseInt(id));

            return statement.executeUpdate() > 0;
        }
    }

    // Menghapus pelanggan
    public boolean delete(String id) throws SQLException {
        String sql = """
                DELETE FROM pelanggan
                WHERE id_pelanggan = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, Integer.parseInt(id));

            return statement.executeUpdate() > 0;
        }
    }
}
