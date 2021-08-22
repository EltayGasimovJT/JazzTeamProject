/*
package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.*;
import org.jazzteam.eltay.gasimov.entity.Warehouse;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
class WarehouseServiceTest {
    @Autowired
    private WarehouseService warehouseService;

    public static Stream<Arguments> ordersAndProcessingPointToTest() {
        OrderProcessingPointDto processingPointToTest = new OrderProcessingPointDto();
        processingPointToTest.setLocation("Russia");
        processingPointToTest.setWarehouse(new WarehouseDto());
        OrderDto firstOrderToTest = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(processingPointToTest)
                .sender(ClientDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .currentLocation(new OrderProcessingPointDto())
                .state(OrderStateDto.builder().build())
                .recipient(ClientDto.builder().build())
                .history(new ArrayList<>())
                .build();

        return Stream.of(
                Arguments.of(firstOrderToTest)
        );
    }

    @ParameterizedTest
    @MethodSource("ordersAndProcessingPointToTest")
    void addWarehouse(OrderDto orderDtoToTest) throws SQLException {
        WarehouseDto warehouseToTest = new WarehouseDto();
        warehouseToTest.setId(1L);
        String expected = "Minsk";
        warehouseToTest.setLocation(expected);
        warehouseToTest.setExpectedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        warehouseToTest.setDispatchedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        warehouseToTest.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        warehouseToTest.setConnectedWarehouses(Arrays.asList(new WarehouseDto(), new WarehouseDto()));
        warehouseService.save(warehouseToTest);

        String actual = warehouseService.findOne(warehouseToTest.getId()).getLocation();

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("ordersAndProcessingPointToTest")
    void deleteWarehouse(OrderDto orderDtoToTest) throws SQLException {
        WarehouseDto firstWarehouseToTest = new WarehouseDto();
        firstWarehouseToTest.setId(1L);
        firstWarehouseToTest.setLocation("");
        firstWarehouseToTest.setExpectedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        firstWarehouseToTest.setDispatchedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        firstWarehouseToTest.setConnectedWarehouses(Arrays.asList(new WarehouseDto(), new WarehouseDto()));
        firstWarehouseToTest.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        WarehouseDto secondWarehouseToTest = new WarehouseDto();
        secondWarehouseToTest.setId(2L);
        secondWarehouseToTest.setLocation("");
        secondWarehouseToTest.setExpectedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        secondWarehouseToTest.setDispatchedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        secondWarehouseToTest.setConnectedWarehouses(Arrays.asList(new WarehouseDto(), new WarehouseDto()));
        secondWarehouseToTest.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        WarehouseDto thirdWarehouseToTest = new WarehouseDto();
        thirdWarehouseToTest.setId(3L);
        thirdWarehouseToTest.setLocation("");
        thirdWarehouseToTest.setExpectedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        thirdWarehouseToTest.setDispatchedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        thirdWarehouseToTest.setConnectedWarehouses(Arrays.asList(new WarehouseDto(), new WarehouseDto()));
        thirdWarehouseToTest.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));

        warehouseService.save(firstWarehouseToTest);
        warehouseService.save(secondWarehouseToTest);
        warehouseService.save(thirdWarehouseToTest);

        warehouseService.delete(secondWarehouseToTest.getId());

        int expected = 2;

        int actual = warehouseService.findAll().size();

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("ordersAndProcessingPointToTest")
    void findAllWarehouses(OrderDto orderDtoToTest) throws SQLException {
        WarehouseDto firstWarehouseToTest = new WarehouseDto();
        firstWarehouseToTest.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        firstWarehouseToTest.setConnectedWarehouses(Arrays.asList(new WarehouseDto(), new WarehouseDto()));
        firstWarehouseToTest.setLocation("Moscow");
        firstWarehouseToTest.setExpectedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        firstWarehouseToTest.setDispatchedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        WarehouseDto secondWarehouse = new WarehouseDto();
        secondWarehouse.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        secondWarehouse.setExpectedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        secondWarehouse.setDispatchedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        secondWarehouse.setConnectedWarehouses(Arrays.asList(new WarehouseDto(), new WarehouseDto()));
        secondWarehouse.setLocation("Moscow");
        WarehouseDto thirdWarehouse = new WarehouseDto();
        thirdWarehouse.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        thirdWarehouse.setExpectedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        thirdWarehouse.setDispatchedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        thirdWarehouse.setConnectedWarehouses(Arrays.asList(new WarehouseDto(), new WarehouseDto()));
        thirdWarehouse.setLocation("Moscow");

        warehouseService.save(firstWarehouseToTest);
        warehouseService.save(secondWarehouse);
        warehouseService.save(thirdWarehouse);

        int expected = 3;

        int actual = warehouseService.findAll().size();

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("ordersAndProcessingPointToTest")
    void getWarehouse(OrderDto orderDtoToTest) throws SQLException {
        WarehouseDto warehouseToTest = new WarehouseDto();
        warehouseToTest.setId(1L);
        String expected = "Vitebsk";
        warehouseToTest.setLocation(expected);
        warehouseToTest.setExpectedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        warehouseToTest.setDispatchedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        warehouseToTest.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));
        warehouseToTest.setConnectedWarehouses(Arrays.asList(new WarehouseDto(), new WarehouseDto()));

        warehouseService.save(warehouseToTest);

        String actual = warehouseService.findOne(warehouseToTest.getId()).getLocation();

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("ordersAndProcessingPointToTest")
    void update(OrderDto orderDtoToTest) throws SQLException {
        WarehouseDto expectedDto = new WarehouseDto();
        expectedDto.setId(1L);
        expectedDto.setLocation("Vitebsk");
        expectedDto.setExpectedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        expectedDto.setDispatchedOrders(Arrays.asList(orderDtoToTest, orderDtoToTest));
        expectedDto.setConnectedWarehouses(Arrays.asList(new WarehouseDto(), new WarehouseDto()));
        expectedDto.setOrderProcessingPoints(Collections.singletonList(
                new OrderProcessingPointDto()
        ));

        warehouseService.save(expectedDto);

        String newLocation = "Minsk";

        expectedDto.setLocation(newLocation);

        expectedDto.setDispatchedOrders(Collections.singletonList(orderDtoToTest));
        expectedDto.setExpectedOrders(Collections.singletonList(orderDtoToTest));

        Warehouse actual = warehouseService.update(expectedDto);

        WarehouseDto actualDto = CustomModelMapper.mapWarehouseToDto(actual);

        Assertions.assertEquals(expectedDto, actualDto);
    }
}
*/
