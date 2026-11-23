package tugasbesarpbo.laundry.model;

public class Petugas extends Staff {

    public Petugas(String username, String password) {
        super(username, password, Role.PETUGAS);
    }

    @Override
    public String getNamaRole() {
        return "Petugas";
    }
}