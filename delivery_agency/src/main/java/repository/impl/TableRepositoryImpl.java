package repository.impl;

import repository.ConnectionRepository;
import repository.TableRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableRepositoryImpl implements TableRepository {
    private ConnectionRepository connectionRepository = new ConnectionRepositoryImpl();

    @Override
    public void executeQuery(String query) throws SQLException {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try (
                    Statement statement = connection.createStatement()
            ) {
                statement.execute(query);
                connection.commit();
            }
        }
    }
}