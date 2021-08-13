package util.impl;

import lombok.extern.slf4j.Slf4j;
import util.DatabaseRepository;
import repository.enums.CreateActionEnum;
import repository.enums.DropActionEnum;
import repository.enums.TruncateActionEnum;
import util.DatabaseService;

import java.sql.SQLException;

@Slf4j
public class DatabaseServiceImpl implements DatabaseService {
    private final DatabaseRepository tableRepository = new DatabaseRepositoryImpl();


    @Override
    public void dropTablesIfExists() throws SQLException {
        for (DropActionEnum action : DropActionEnum.values()) {
            tableRepository.executeQuery(action.getQuery());
        }
    }


    @Override
    public void createTables() throws SQLException {
        for (CreateActionEnum action : CreateActionEnum.values()) {
            tableRepository.executeQuery(action.getQuery());
        }
    }

    @Override
    public void truncateTables() throws SQLException {
        for (TruncateActionEnum action : TruncateActionEnum.values()) {
            tableRepository.executeQuery(action.getQuery());
        }
    }

    @Override
    public void executeSqlScript(String path) {
        tableRepository.executeSqlScript(path);
    }
}
