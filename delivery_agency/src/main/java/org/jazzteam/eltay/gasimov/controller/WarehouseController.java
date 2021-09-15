package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.entity.Warehouse;
import org.jazzteam.eltay.gasimov.service.WarehouseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    public List<String> updateWarehouse(@RequestBody WarehouseDto newWarehouse) {
        List<String> processingPointsIds = new ArrayList<>();
        WarehouseDto updateWarehouse = modelMapper.map(warehouseService.update(newWarehouse), WarehouseDto.class);
        for (OrderProcessingPointDto orderProcessingPoint : updateWarehouse.getOrderProcessingPoints()) {
            processingPointsIds.add(orderProcessingPoint.getId().toString());
        }
        return getWarehouseAsString(updateWarehouse, processingPointsIds);
    }

    @GetMapping(path = WAREHOUSES_BY_ID_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<String> findById(@PathVariable Long id) {
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
