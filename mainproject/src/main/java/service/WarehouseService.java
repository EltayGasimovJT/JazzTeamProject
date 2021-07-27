package service;


import entity.Warehouse;

import java.util.List;

public interface WarehouseService {
    Warehouse addWarehouse(Warehouse warehouse);

    void deleteWarehouse(Warehouse warehouse);

    List<Warehouse> findAllWarehouses();

    Warehouse getWarehouse(long id);

    Warehouse update(Warehouse warehouse);
}
