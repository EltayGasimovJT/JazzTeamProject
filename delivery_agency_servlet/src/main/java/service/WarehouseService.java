package service;


import entity.Warehouse;

import java.sql.SQLException;
import java.util.List;

public interface WarehouseService {
    Warehouse addWarehouse(Warehouse warehouse) throws SQLException;

    void deleteWarehouse(Warehouse warehouse);

    List<Warehouse> findAllWarehouses() throws SQLException;

    Warehouse getWarehouse(long id) throws SQLException;

    Warehouse update(Warehouse warehouse) throws SQLException;
}
