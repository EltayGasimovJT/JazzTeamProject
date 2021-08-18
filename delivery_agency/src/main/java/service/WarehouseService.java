package service;

import dto.WarehouseDto;
import entity.Warehouse;

import java.sql.SQLException;
import java.util.List;

public interface WarehouseService {
    Warehouse save(WarehouseDto warehouseDtoToSave) throws SQLException;

    void delete(Long idForDelete);

    List<Warehouse> findAll() throws SQLException;

    Warehouse findOne(long idForSearch) throws SQLException;

    Warehouse update(WarehouseDto warehouseDtoToUpdate) throws SQLException;
}