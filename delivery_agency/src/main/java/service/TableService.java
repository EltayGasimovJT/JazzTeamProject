package service;

public interface TableService {
    void dropTablesIfExists();

    void createTables();

    void truncateTables();
}
