package tugasbesarpbo.laundry.model;

public class UserFactory {

    public static User createUser(Role role, String username, String password) {
        if (role == null) {
            return null;
        }

        switch (role) {
            case ADMIN:
                return new Admin(username, password);
            case KASIR:
                return new Kasir(username, password);
            case PETUGAS:
                return new Petugas(username, password);
            default:
                return null;
        }
    }
}