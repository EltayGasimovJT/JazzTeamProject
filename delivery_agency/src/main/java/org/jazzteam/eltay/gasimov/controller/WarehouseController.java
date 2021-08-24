package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.entity.Warehouse;
import org.jazzteam.eltay.gasimov.service.WarehouseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/warehouses")
    public @ResponseBody
    WarehouseDto addNewWarehouse(@RequestBody WarehouseDto warehouseDto) throws SQLException {
        return modelMapper.map(warehouseService.save(warehouseDto), WarehouseDto.class);
    }

    @GetMapping(path = "/warehouses")
    public @ResponseBody
    Iterable<Long> findAllWarehouses() throws SQLException {
        List<Long> listOfWarehousesId = new ArrayList<>();
        for (Warehouse warehouse : warehouseService.findAll()) {
            listOfWarehousesId.add(warehouse.getId());
        }
        return listOfWarehousesId;
    }

    @DeleteMapping(path = "/warehouses/{id}")
    public void deleteWarehouse(@PathVariable Long id) {
        warehouseService.delete(id);
    }

    @PutMapping("/warehouses")
    public List<String> updateCoefficient(@RequestBody WarehouseDto newWarehouse) throws SQLException {
        if (warehouseService.findOne(newWarehouse.getId()) == null) {
            List<String> processingPointsIds = new ArrayList<>();
            final WarehouseDto savedWarehouse = modelMapper.map(warehouseService.save(newWarehouse), WarehouseDto.class);
            for (OrderProcessingPointDto orderProcessingPoint : savedWarehouse.getOrderProcessingPoints()) {
                processingPointsIds.add(orderProcessingPoint.getId().toString());
            }
            return getWarehouseAsString(savedWarehouse, processingPointsIds);
        } else {
            List<String> processingPointsIds = new ArrayList<>();
            final WarehouseDto updateWarehouse = modelMapper.map(warehouseService.update(newWarehouse), WarehouseDto.class);
            for (OrderProcessingPointDto orderProcessingPoint : updateWarehouse.getOrderProcessingPoints()) {
                processingPointsIds.add(orderProcessingPoint.getId().toString());
            }
            return getWarehouseAsString(updateWarehouse, processingPointsIds);
        }
    }

    @GetMapping(path = "/warehouses/{id}")
    public @ResponseBody
    List<String> findById(@PathVariable Long id) throws SQLException {
        WarehouseDto foundWarehouse = modelMapper.map(warehouseService.findOne(id), WarehouseDto.class);
        List<String> processingPointsIds = new ArrayList<>();
        for (OrderProcessingPointDto orderProcessingPoint : foundWarehouse.getOrderProcessingPoints()) {
            processingPointsIds.add(orderProcessingPoint.getId().toString());
        }
        return getWarehouseAsString(foundWarehouse, processingPointsIds);
    }

    private List<String> getWarehouseAsString(WarehouseDto foundWarehouse, List<String> processingPointsIds) {
        return Arrays.asList("id: " + foundWarehouse.getId().toString(),
                "Location: " + foundWarehouse.getLocation(), "WorkingPlaceType: " + foundWarehouse.getWorkingPlaceType().toString(),
                "ProcessingPoints: " + processingPointsIds);
    }
}
