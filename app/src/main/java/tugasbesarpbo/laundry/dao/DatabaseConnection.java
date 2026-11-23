package tugasbesarpbo.laundry.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final String DEFAULT_URL =
            "jdbc:postgresql://localhost:5432/laundry_db";

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        String url = DEFAULT_URL;
        String user = System.getenv("LAUNDRY_DB_USER");
        String password = System.getenv("LAUNDRY_DB_PASSWORD");

        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
            url = props.getProperty("db.url", DEFAULT_URL);
            user = props.getProperty("db.user", user);
            password = props.getProperty("db.password", password);
        } catch (IOException ignored) {
        }

        if (user == null || password == null) {
            throw new SQLException(
                    "Konfigurasi database belum diatur di config.properties "
                    + "maupun environment variable."
            );
        }

        return DriverManager.getConnection(url, user, password);
    }
}