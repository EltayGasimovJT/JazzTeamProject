package repository.impl;

import lombok.extern.slf4j.Slf4j;
import repository.ConnectionRepository;
import util.PropertyUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class ConnectionRepositoryImpl implements ConnectionRepository {
    private final PropertyUtil propertyUtil = new PropertyUtil();
    private static final String DATABASE_URL = "datasource.url";
    private static final String DATABASE_USER_NAME = "datasource.username";
    private static final String DATABASE_PASSWORD = "datasource.password";
    private static final String DATABASE_DRIVER = "datasource.driver";

    @Override
    public Connection getConnection() {
        try {
            Class.forName(propertyUtil.getProperty(DATABASE_DRIVER));
            return DriverManager.getConnection(
                    propertyUtil.getProperty(DATABASE_URL),
                    propertyUtil.getProperty(DATABASE_USER_NAME),
                    propertyUtil.getProperty(DATABASE_PASSWORD)
            );
        } catch (SQLException | ClassNotFoundException e) {
            log.error(e.getMessage());
            throw new IllegalStateException("App cannot get connection to Database");
        }
    }
}
