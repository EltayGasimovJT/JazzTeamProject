package service.impl;

import lombok.extern.slf4j.Slf4j;
import repository.ConnectionRepository;
import repository.TableRepository;
import repository.enums.CreateActionEnum;
import repository.enums.DropActionEnum;
import repository.impl.ConnectionRepositoryImpl;
import repository.impl.TableRepositoryImpl;
import service.TableService;

import java.sql.Connection;
import java.sql.SQLException;


@Slf4j
public class TableServiceImpl implements TableService {
    private TableRepository tableRepository = new TableRepositoryImpl();
    private ConnectionRepository connectionRepository = new ConnectionRepositoryImpl();


    @Override
    public void dropTablesIfExists() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (DropActionEnum action : DropActionEnum.values()) {
                    tableRepository.executeQuery(connection, action.getQuery());
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                log.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void createTables() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (CreateActionEnum action : CreateActionEnum.values()) {
                    tableRepository.executeQuery(connection, action.getQuery());
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                log.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }


}
