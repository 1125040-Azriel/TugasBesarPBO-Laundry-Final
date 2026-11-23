package tugasbesarpbo.laundry.dao;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {
    List<T> getAll() throws SQLException;
}