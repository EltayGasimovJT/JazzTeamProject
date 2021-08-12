package service;

import dto.WarehouseDto;
import entity.Warehouse;

import java.sql.SQLException;
import java.util.List;

public interface WarehouseService {
    WarehouseDto addWarehouse(WarehouseDto warehouse) throws SQLException;

    void deleteWarehouse(Long id);

    List<WarehouseDto> findAllWarehouses() throws SQLException;

    WarehouseDto getWarehouse(long id) throws SQLException;

    WarehouseDto update(WarehouseDto warehouse) throws SQLException;
}
