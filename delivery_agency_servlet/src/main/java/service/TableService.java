package service;

import java.sql.SQLException;

public interface TableService {
    void dropTablesIfExists() throws SQLException;

    void createTables() throws SQLException;

    void truncateTables() throws SQLException;
}
