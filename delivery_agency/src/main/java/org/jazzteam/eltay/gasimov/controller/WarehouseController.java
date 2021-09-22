package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.entity.Warehouse;
import org.jazzteam.eltay.gasimov.service.WarehouseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.jazzteam.eltay.gasimov.util.Constants.WAREHOUSES_BY_ID_URL;
import static org.jazzteam.eltay.gasimov.util.Constants.WAREHOUSES_URL;

@RestController
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = WAREHOUSES_URL)
    public @ResponseBody
    WarehouseDto addNewWarehouse(@RequestBody WarehouseDto warehouseDto) {
        return modelMapper.map(warehouseService.save(warehouseDto), WarehouseDto.class);
    }

    @GetMapping(path = WAREHOUSES_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<Long> findAllWarehouses() {
        List<Long> listOfWarehousesId = new ArrayList<>();
        for (Warehouse warehouse : warehouseService.findAll()) {
            listOfWarehousesId.add(warehouse.getId());
        }
        return listOfWarehousesId;
    }

    @DeleteMapping(path = WAREHOUSES_BY_ID_URL)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWarehouse(@PathVariable Long id) {
        warehouseService.delete(id);
    }

    @PutMapping(path = WAREHOUSES_URL)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public WarehouseDto updateWarehouse(@RequestBody WarehouseDto newWarehouse) {
        return modelMapper.map(warehouseService.update(newWarehouse), WarehouseDto.class);
    }

    @GetMapping(path = WAREHOUSES_BY_ID_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    WarehouseDto findById(@PathVariable Long id) {
        return modelMapper.map(warehouseService.findOne(id), WarehouseDto.class);
    }
}
