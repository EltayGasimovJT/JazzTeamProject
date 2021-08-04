package repository;

import java.sql.Connection;

public interface ConnectionRepository {
    Connection getConnection();
}
