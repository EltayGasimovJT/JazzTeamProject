package util.impl;

import lombok.extern.slf4j.Slf4j;
import util.ConnectionRepositoryUtil;
import util.DatabaseRepository;
import util.DatabaseScriptUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class DatabaseRepositoryImpl implements DatabaseRepository {
    private final ConnectionRepositoryUtil connectionRepository = new ConnectionRepositoryUtilImpl();

    @Override
    public void executeQuery(String query) throws SQLException {
        try (Connection connection = connectionRepository.getConnection()) {
            try (
                    Statement statement = connection.createStatement()
            ) {
                statement.execute(query);
            }
        }
    }

    @Override
    public void executeSqlScript(String path) {
        try (Connection connection = connectionRepository.getConnection()) {
            DatabaseScriptUtil.executeSQL(path, connection);
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }
    }
}
