package service;

import dto.OrderProcessingPointDto;
import dto.WarehouseDto;
import entity.OrderProcessingPoint;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.modelmapper.ModelMapper;
import service.impl.OrderProcessingPointServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class OrderProcessingPointServiceTest {
    private final OrderProcessingPointService orderProcessingPointService = new OrderProcessingPointServiceImpl();
    private final ModelMapper modelMapper = new ModelMapper();


    private static Stream<Arguments> testOrderProcessingPoints() {
        OrderProcessingPointDto firstProcessingPointToTest = new OrderProcessingPointDto();
        firstProcessingPointToTest.setId(1L);
        firstProcessingPointToTest.setLocation("Minsk");
        firstProcessingPointToTest.setWarehouse(new WarehouseDto());

        OrderProcessingPointDto secondProcessingPointToTest = new OrderProcessingPointDto();
        secondProcessingPointToTest.setId(2L);
        secondProcessingPointToTest.setLocation("Moscow");
        secondProcessingPointToTest.setWarehouse(new WarehouseDto());

        OrderProcessingPointDto thirdProcessingPointToTest = new OrderProcessingPointDto();
        thirdProcessingPointToTest.setId(3L);
        thirdProcessingPointToTest.setLocation("Grodno");
        thirdProcessingPointToTest.setWarehouse(new WarehouseDto());

        return Stream.of(
                Arguments.of(firstProcessingPointToTest, "Minsk"),
                Arguments.of(secondProcessingPointToTest, "Moscow"),
                Arguments.of(thirdProcessingPointToTest, "Grodno")
        );
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("testOrderProcessingPoints")
    void addOrderProcessingPoint(OrderProcessingPointDto orderProcessingPoint, String expectedLocation) {
        orderProcessingPointService.save(orderProcessingPoint);

        String actualLocation = orderProcessingPointService.findOne(orderProcessingPoint.getId()).getLocation();
        Assertions.assertEquals(expectedLocation, actualLocation);
    }

    @Test
    void deleteOrderProcessingPoint() throws SQLException {
        OrderProcessingPointDto firstProcessingPoint = new OrderProcessingPointDto();
        firstProcessingPoint.setId(1L);
        firstProcessingPoint.setWarehouse(new WarehouseDto());

        OrderProcessingPointDto secondProcessingPoint = new OrderProcessingPointDto();
        secondProcessingPoint.setId(2L);
        secondProcessingPoint.setWarehouse(new WarehouseDto());

        OrderProcessingPointDto thirdProcessingPoint = new OrderProcessingPointDto();
        thirdProcessingPoint.setId(3L);
        thirdProcessingPoint.setWarehouse(new WarehouseDto());

        orderProcessingPointService.save(firstProcessingPoint);
        orderProcessingPointService.save(secondProcessingPoint);
        orderProcessingPointService.save(thirdProcessingPoint);

        orderProcessingPointService.delete(firstProcessingPoint.getId());

        List<OrderProcessingPoint> actualProcessingPoints = orderProcessingPointService.findAll();

        List<OrderProcessingPointDto> actualProcessingPointDtos = new ArrayList<>();

        for (OrderProcessingPoint actualProcessingPoint : actualProcessingPoints) {
            WarehouseDto actualWarehouse = modelMapper.map(actualProcessingPoint.getWarehouse(), WarehouseDto.class);
            OrderProcessingPointDto actualProcessingPointDto = modelMapper.map(actualProcessingPoint, OrderProcessingPointDto.class);
            actualProcessingPointDto.setWarehouse(actualWarehouse);
            actualProcessingPointDtos.add(actualProcessingPointDto);
        }

        Assertions.assertEquals(Arrays.asList(secondProcessingPoint, thirdProcessingPoint)
                , actualProcessingPointDtos);
    }

    @Test
    void findAllOrderProcessingPoints() throws SQLException {
        OrderProcessingPointDto firstProcessingPoint = new OrderProcessingPointDto();
        firstProcessingPoint.setWarehouse(new WarehouseDto());
        OrderProcessingPointDto secondProcessingPoint = new OrderProcessingPointDto();
        secondProcessingPoint.setWarehouse(new WarehouseDto());
        OrderProcessingPointDto thirdProcessingPoint = new OrderProcessingPointDto();
        thirdProcessingPoint.setWarehouse(new WarehouseDto());

        orderProcessingPointService.save(firstProcessingPoint);
        orderProcessingPointService.save(secondProcessingPoint);
        orderProcessingPointService.save(thirdProcessingPoint);

        List<OrderProcessingPoint> actualOrderProcessingPoints = orderProcessingPointService.findAll();

        List<OrderProcessingPointDto> actualProcessingPointDtos = new ArrayList<>();

        for (OrderProcessingPoint actualOrderProcessingPoint : actualOrderProcessingPoints) {
            WarehouseDto actualWarehouse = modelMapper.map(actualOrderProcessingPoint.getWarehouse(), WarehouseDto.class);
            OrderProcessingPointDto actualProcessingOrder = modelMapper.map(actualOrderProcessingPoint, OrderProcessingPointDto.class);
            actualProcessingOrder.setWarehouse(actualWarehouse);
            actualProcessingPointDtos.add(actualProcessingOrder);
        }

        Assertions.assertEquals(Arrays.asList(firstProcessingPoint, secondProcessingPoint, thirdProcessingPoint)
                , actualProcessingPointDtos);
    }

    @SneakyThrows
    @Test
    void getOrderProcessingPoint() {
        OrderProcessingPointDto processingPoint = new OrderProcessingPointDto();
        processingPoint.setId(1L);
        processingPoint.setLocation("Minsk");
        processingPoint.setWarehouse(new WarehouseDto());

        OrderProcessingPointDto expectedProcessingPointDto = new OrderProcessingPointDto();
        expectedProcessingPointDto.setId(2L);
        expectedProcessingPointDto.setLocation("Moscow");
        expectedProcessingPointDto.setWarehouse(new WarehouseDto());

        orderProcessingPointService.save(processingPoint);
        orderProcessingPointService.save(expectedProcessingPointDto);

        OrderProcessingPoint actual = orderProcessingPointService.findOne(2);

        OrderProcessingPointDto actualProcessingPointDto = modelMapper.map(actual, OrderProcessingPointDto.class);

        actualProcessingPointDto.setWarehouse(modelMapper.map(actual.getWarehouse(), WarehouseDto.class));

        Assertions.assertEquals(expectedProcessingPointDto, actualProcessingPointDto);
    }

    @Test
    void update() throws SQLException {
        OrderProcessingPointDto expectedProcessingPointDto = new OrderProcessingPointDto();
        expectedProcessingPointDto.setId(1L);
        expectedProcessingPointDto.setLocation("Minsk");
        expectedProcessingPointDto.setWarehouse(new WarehouseDto());

        orderProcessingPointService.save(expectedProcessingPointDto);

        String newLocation = "Homel";

        expectedProcessingPointDto.setLocation(newLocation);

        OrderProcessingPoint actual = orderProcessingPointService.update(expectedProcessingPointDto);

        OrderProcessingPointDto actualProcessingPointDto = modelMapper.map(actual, OrderProcessingPointDto.class);

        actualProcessingPointDto.setWarehouse(modelMapper.map(actual.getWarehouse(), WarehouseDto.class));

        Assertions.assertEquals(expectedProcessingPointDto, actualProcessingPointDto);
    }
}