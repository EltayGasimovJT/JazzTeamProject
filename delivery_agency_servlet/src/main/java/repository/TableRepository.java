package repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface TableRepository {
    void executeQuery(String query) throws SQLException;
}
