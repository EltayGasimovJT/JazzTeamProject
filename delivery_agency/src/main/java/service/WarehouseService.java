package service;

import dto.WarehouseDto;
import entity.Warehouse;

import java.sql.SQLException;
import java.util.List;

public interface WarehouseService {
    Warehouse save(WarehouseDto warehouse) throws SQLException;

    void delete(Long id);

    List<Warehouse> findAll() throws SQLException;

    Warehouse findOne(long id) throws SQLException;

    Warehouse update(WarehouseDto warehouse) throws SQLException;
}