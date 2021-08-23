package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.service.WarehouseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.stream.Collectors;

@RestController
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/warehouses")
    public @ResponseBody
    WarehouseDto addNewCoefficient(@RequestBody WarehouseDto warehouseDto) throws SQLException {
        return modelMapper.map(warehouseService.save(warehouseDto), WarehouseDto.class);
    }

    @GetMapping(path = "/warehouses")
    public @ResponseBody
    Iterable<WarehouseDto> findAllCoefficients() throws SQLException {
        return warehouseService.findAll().stream()
                .map(orderProcessingPoint -> modelMapper.map(orderProcessingPoint, WarehouseDto.class))
                .collect(Collectors.toSet());
    }

    @DeleteMapping(path = "/warehouses/{id}")
    public void deleteCoefficient(@PathVariable Long id) throws SQLException {
        warehouseService.delete(id);
    }

    @PutMapping("/warehouses")
    public WarehouseDto updateCoefficient(@RequestBody WarehouseDto newWarehouse) throws SQLException {
        if (warehouseService.findOne(newWarehouse.getId()) == null) {
            return modelMapper.map(warehouseService.save(newWarehouse), WarehouseDto.class);
        } else {
            return modelMapper.map(warehouseService.update(newWarehouse), WarehouseDto.class);
        }
    }
}
