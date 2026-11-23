package tugasbesarpbo.laundry.model;

public class Kasir extends Staff {

    public Kasir(String username, String password) {
        super(username, password, Role.KASIR);
    }

    @Override
    public String getNamaRole() {
        return "Kasir";
    }
}