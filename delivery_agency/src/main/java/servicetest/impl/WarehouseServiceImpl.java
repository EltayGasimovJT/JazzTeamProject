package servicetest.impl;

import entity.Warehouse;
import repository.WareHouseRepository;
import repository.impl.WareHouseRepositoryImpl;
import servicetest.WarehouseService;

import java.util.List;

public class WarehouseServiceImpl implements WarehouseService {
    private final WareHouseRepository wareHouseRepository = new WareHouseRepositoryImpl();

    @Override
    public Warehouse addWarehouse(Warehouse warehouse) {
        return wareHouseRepository.save(warehouse);
    }

    @Override
    public void deleteWarehouse(Warehouse warehouse) {
        wareHouseRepository.delete(warehouse);
    }

    @Override
    public List<Warehouse> findAllWarehouses() {
        return wareHouseRepository.findAll();
    }

    @Override
    public Warehouse getWarehouse(long id) {
        return wareHouseRepository.findOne(id);
    }

    @Override
    public Warehouse update(Warehouse warehouse) {
        return wareHouseRepository.update(warehouse);
    }
}
