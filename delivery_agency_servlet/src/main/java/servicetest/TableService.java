package servicetest;

public interface TableService {
    void dropTablesIfExists();

    void createTables();
}
