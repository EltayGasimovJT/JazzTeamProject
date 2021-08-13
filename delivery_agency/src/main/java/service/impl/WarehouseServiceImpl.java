package service.impl;

import dto.WarehouseDto;
import entity.Warehouse;
import repository.WareHouseRepository;
import repository.impl.WarehouseRepositoryImpl;
import service.WarehouseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.ConvertUtil.fromDtoToWarehouse;
import static util.ConvertUtil.fromWarehouseToDTO;

public class WarehouseServiceImpl implements WarehouseService {
    private final WareHouseRepository wareHouseRepository = new WarehouseRepositoryImpl();

    @Override
    public WarehouseDto addWarehouse(WarehouseDto warehouse) {
        return fromWarehouseToDTO(wareHouseRepository.save(fromDtoToWarehouse(warehouse)));
    }

    @Override
    public void deleteWarehouse(Long id) {
        wareHouseRepository.delete(id);
    }

    @Override
    public List<WarehouseDto> findAllWarehouses() {
        List<Warehouse> warehouses = wareHouseRepository.findAll();
        List<WarehouseDto> warehouseDtos = new ArrayList<>();

        for (Warehouse warehouse : warehouses) {
            warehouseDtos.add(fromWarehouseToDTO(warehouse));
        }
        return warehouseDtos;
    }

    @Override
    public WarehouseDto getWarehouse(long id) {
        return fromWarehouseToDTO(wareHouseRepository.findOne(id));
    }

    @Override
    public WarehouseDto update(WarehouseDto warehouse) throws SQLException {
        return fromWarehouseToDTO(wareHouseRepository.update(fromDtoToWarehouse(warehouse)));
    }

}