package repository.impl;

import util.ConnectionRepositoryUtil;
import repository.TableRepository;
import util.impl.ConnectionRepositoryUtilImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableRepositoryImpl implements TableRepository {
    private final ConnectionRepositoryUtil connectionRepository = new ConnectionRepositoryUtilImpl();

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
