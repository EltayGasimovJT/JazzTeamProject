package util;

import java.sql.SQLException;

public interface DatabaseRepository {
    void executeQuery(String query) throws SQLException;

    void executeSqlScript(String path);
}
