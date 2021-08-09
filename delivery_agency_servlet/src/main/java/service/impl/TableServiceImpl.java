package service.impl;

import lombok.extern.slf4j.Slf4j;
import repository.TableRepository;
import repository.enums.CreateActionEnum;
import repository.enums.DropActionEnum;
import repository.enums.TruncateActionEnum;
import repository.impl.TableRepositoryImpl;
import service.TableService;

import java.sql.SQLException;

@Slf4j
public class TableServiceImpl implements TableService {
    private TableRepository tableRepository = new TableRepositoryImpl();


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
}
