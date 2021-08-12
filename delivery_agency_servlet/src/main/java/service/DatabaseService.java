package service;

import java.sql.SQLException;

public interface DatabaseService {
    void dropTablesIfExists() throws SQLException;

    void createTables() throws SQLException;

    void truncateTables() throws SQLException;

    void executeSqlScript(String path);
}
