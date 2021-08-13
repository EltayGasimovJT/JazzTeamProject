package service;

import dto.OrderProcessingPointDto;
import dto.WarehouseDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.OrderProcessingPointServiceImpl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class OrderProcessingPointServiceTest {
    private final OrderProcessingPointService orderProcessingPointService = new OrderProcessingPointServiceImpl();

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

        List<OrderProcessingPointDto> actualProcessingPoints = orderProcessingPointService.findAll();

        Assertions.assertEquals(Arrays.asList(secondProcessingPoint, thirdProcessingPoint)
                , actualProcessingPoints);
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

        List<OrderProcessingPointDto> actualOrderProcessingPoints = orderProcessingPointService.findAll();

        Assertions.assertEquals(Arrays.asList(firstProcessingPoint, secondProcessingPoint, thirdProcessingPoint)
                , actualOrderProcessingPoints);
    }

    @SneakyThrows
    @Test
    void getOrderProcessingPoint() {
        OrderProcessingPointDto processingPoint = new OrderProcessingPointDto();
        processingPoint.setId(1L);
        processingPoint.setLocation("Minsk");
        processingPoint.setWarehouse(new WarehouseDto());

        OrderProcessingPointDto expected = new OrderProcessingPointDto();
        expected.setId(2L);
        expected.setLocation("Moscow");
        expected.setWarehouse(new WarehouseDto());


        orderProcessingPointService.save(processingPoint);
        orderProcessingPointService.save(expected);


        OrderProcessingPointDto actual = orderProcessingPointService.findOne(2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() throws SQLException {
        OrderProcessingPointDto expected = new OrderProcessingPointDto();
        expected.setId(1L);
        expected.setLocation("Minsk");
        expected.setWarehouse(new WarehouseDto());

        orderProcessingPointService.save(expected);

        String newLocation = "Homel";

        expected.setLocation(newLocation);

        OrderProcessingPointDto actual = orderProcessingPointService.update(expected);

        Assertions.assertEquals(expected, actual);
    }
}