package service.impl;

import entity.Warehouse;
import repository.WareHouseRepository;
import repository.impl.WarehouseRepositoryImpl;
import service.WarehouseService;

import java.sql.SQLException;
import java.util.List;

public class WarehouseServiceImpl implements WarehouseService {
    private final WareHouseRepository wareHouseRepository = new WarehouseRepositoryImpl();

    @Override
    public Warehouse addWarehouse(Warehouse warehouse) throws SQLException {
        return wareHouseRepository.save(warehouse);
    }

    @Override
    public void deleteWarehouse(Warehouse warehouse) {
        wareHouseRepository.delete(warehouse);
    }

    @Override
    public List<Warehouse> findAllWarehouses() throws SQLException {
        return wareHouseRepository.findAll();
    }

    @Override
    public Warehouse getWarehouse(long id) throws SQLException {
        return wareHouseRepository.findOne(id);
    }

    @Override
    public Warehouse update(Warehouse warehouse) throws SQLException {
        return wareHouseRepository.update(warehouse);
    }
}
