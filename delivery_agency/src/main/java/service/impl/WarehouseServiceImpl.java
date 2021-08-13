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
    public Warehouse save(WarehouseDto warehouse) {
        return fromWarehouseToDTO(wareHouseRepository.save(fromDtoToWarehouse(warehouse)));
    }

    @Override
    public void delete(Long id) {
        wareHouseRepository.delete(id);
    }

    @Override
    public List<Warehouse> findAll() {
        List<Warehouse> warehouses = wareHouseRepository.findAll();
        List<WarehouseDto> warehouseDtos = new ArrayList<>();

        for (Warehouse warehouse : warehouses) {
            warehouseDtos.add(fromWarehouseToDTO(warehouse));
        }
        return warehouseDtos;
    }

    @Override
    public Warehouse findOne(long id) {
        return wareHouseRepository.findOne(id));
    }

    @Override
    public Warehouse update(WarehouseDto warehouse) throws SQLException {

        return wareHouseRepository.update();
    }
}