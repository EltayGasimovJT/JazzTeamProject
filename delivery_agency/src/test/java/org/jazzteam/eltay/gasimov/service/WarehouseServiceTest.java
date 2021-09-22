package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.WarehouseDto;
import org.jazzteam.eltay.gasimov.entity.Warehouse;
import org.jazzteam.eltay.gasimov.entity.WorkingPlaceType;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class WarehouseServiceTest {
    @Autowired
    private WarehouseService warehouseService;

    @Test
    void addWarehouse() {
        WarehouseDto warehouseToTest = new WarehouseDto();
        warehouseToTest.setId(1L);
        String expected = "Minsk";
        warehouseToTest.setLocation(expected);
        warehouseToTest.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);

        warehouseToTest.setConnectedWarehouses(Arrays.asList(new WarehouseDto(), new WarehouseDto()));
        Warehouse savedWarehouse = warehouseService.save(warehouseToTest);

        String actual = warehouseService.findOne(savedWarehouse.getId()).getLocation();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteWarehouse() {
        WarehouseDto firstWarehouseToTest = new WarehouseDto();
        firstWarehouseToTest.setLocation("Minsk");
        firstWarehouseToTest.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);

        WarehouseDto secondWarehouseToTest = new WarehouseDto();
        secondWarehouseToTest.setLocation("Moscow");
        secondWarehouseToTest.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);

        Warehouse savedFirst = warehouseService.save(firstWarehouseToTest);
        Warehouse savedWarehouse = warehouseService.save(secondWarehouseToTest);

        warehouseService.delete(savedWarehouse.getId());

        List<Warehouse> actual = warehouseService.findAll();

        Assertions.assertEquals(Collections.singletonList(savedFirst), actual);
    }

    @Test
    void findAllWarehouses() {
        WarehouseDto firstWarehouseToTest = new WarehouseDto();
        firstWarehouseToTest.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        firstWarehouseToTest.setConnectedWarehouses(Arrays.asList(new WarehouseDto(), new WarehouseDto()));
        firstWarehouseToTest.setLocation("Belarus");
        firstWarehouseToTest.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);

        WarehouseDto secondWarehouse = new WarehouseDto();
        secondWarehouse.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        secondWarehouse.setConnectedWarehouses(Arrays.asList(new WarehouseDto(), new WarehouseDto()));
        secondWarehouse.setLocation("Moscow");
        secondWarehouse.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);

        WarehouseDto thirdWarehouse = new WarehouseDto();
        thirdWarehouse.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        thirdWarehouse.setConnectedWarehouses(Arrays.asList(new WarehouseDto(), new WarehouseDto()));
        thirdWarehouse.setLocation("London");
        thirdWarehouse.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);

        Warehouse savedFirst = warehouseService.save(firstWarehouseToTest);
        Warehouse savedSecond = warehouseService.save(secondWarehouse);
        Warehouse savedThird = warehouseService.save(thirdWarehouse);

        List<Warehouse> actual = warehouseService.findAll();

        Assertions.assertEquals(Arrays.asList(savedFirst, savedSecond, savedThird), actual);
    }

    @Test
    void getWarehouseById() {
        WarehouseDto expectedDto = new WarehouseDto();
        expectedDto.setLocation("Vitebsk");
        expectedDto.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);

        Warehouse expected = warehouseService.save(expectedDto);
        Warehouse actual = warehouseService.findOne(expected.getId());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() {
        WarehouseDto expectedDto = new WarehouseDto();
        expectedDto.setLocation("Vitebsk");
        expectedDto.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);

        Warehouse savedWarehouse = warehouseService.save(expectedDto);

        String newLocation = "Minsk";

        savedWarehouse.setLocation(newLocation);

        Warehouse actual = warehouseService.update(CustomModelMapper.mapWarehouseToDto(savedWarehouse));
        expectedDto.setId(savedWarehouse.getId());
        WarehouseDto actualDto = CustomModelMapper.mapWarehouseToDto(actual);

        Assertions.assertEquals(expectedDto, actualDto);
    }
}