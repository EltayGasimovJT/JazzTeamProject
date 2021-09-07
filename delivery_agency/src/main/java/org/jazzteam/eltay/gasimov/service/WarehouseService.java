package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.entity.Warehouse;

import java.util.List;

public interface WarehouseService {
    Warehouse save(WarehouseDto warehouseDtoToSave);

    void delete(Long idForDelete);

    List<Warehouse> findAll();

    Warehouse findOne(long idForSearch);

    Warehouse findByLocation(String idForSearch);

    Warehouse update(WarehouseDto warehouseDtoToUpdate);
}