package tugasbesarpbo.laundry.controller;

import java.sql.SQLException;

import tugasbesarpbo.laundry.dao.UserDAO;
import tugasbesarpbo.laundry.model.Role;
import tugasbesarpbo.laundry.model.User;
import tugasbesarpbo.laundry.model.UserFactory;
import tugasbesarpbo.laundry.view.DashboardView;
import tugasbesarpbo.laundry.view.LoginView;

public class LoginController {

    private final LoginView view;
    private final UserDAO userDAO;

    public LoginController() {
        view = new LoginView();
        userDAO = new UserDAO();

        view.setLoginAction(this::handleLogin);
    }

    private void handleLogin() {
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            view.setStatus("Username dan password wajib diisi.");
            return;
        }

        try {
            Role role = userDAO.authenticate(username, password);

            if (role == null) {
                view.setStatus(
                        "Login gagal. Periksa username dan password."
                );
                return;
            }

            User user = UserFactory.createUser(role, username, password);

            if (user == null) {
                view.setStatus("Role pengguna tidak valid.");
                return;
            }

            DashboardView dashboard = new DashboardView(user);
            dashboard.setVisible(true);
            view.dispose();

        } catch (SQLException e) {
            view.setStatus(
                    "Gagal mengakses database. Periksa koneksi."
            );
            System.err.println(
                    "Kesalahan database: " + e.getMessage()
            );
        }
    }

    public void start() {
        view.showWindow();
    }
}