package service;

import dto.OrderProcessingPointDto;
import entity.Warehouse;
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
        firstProcessingPointToTest.setWarehouse(new Warehouse());

        OrderProcessingPointDto secondProcessingPointToTest = new OrderProcessingPointDto();
        secondProcessingPointToTest.setId(2L);
        secondProcessingPointToTest.setLocation("Moscow");
        secondProcessingPointToTest.setWarehouse(new Warehouse());

        OrderProcessingPointDto thirdProcessingPointToTest = new OrderProcessingPointDto();
        thirdProcessingPointToTest.setId(3L);
        thirdProcessingPointToTest.setLocation("Grodno");
        thirdProcessingPointToTest.setWarehouse(new Warehouse());

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
        orderProcessingPointService.addOrderProcessingPoint(orderProcessingPoint);

        String actualLocation = orderProcessingPointService.getOrderProcessingPoint(orderProcessingPoint.getId()).getLocation();
        Assertions.assertEquals(expectedLocation, actualLocation);
    }

    @Test
    void deleteOrderProcessingPoint() throws SQLException {
        OrderProcessingPointDto firstProcessingPoint = new OrderProcessingPointDto();
        firstProcessingPoint.setId(1L);

        OrderProcessingPointDto secondProcessingPoint = new OrderProcessingPointDto();
        firstProcessingPoint.setId(2L);

        OrderProcessingPointDto thirdProcessingPoint = new OrderProcessingPointDto();
        firstProcessingPoint.setId(3L);

        orderProcessingPointService.addOrderProcessingPoint(firstProcessingPoint);
        orderProcessingPointService.addOrderProcessingPoint(secondProcessingPoint);
        orderProcessingPointService.addOrderProcessingPoint(thirdProcessingPoint);

        orderProcessingPointService.deleteOrderProcessingPoint(firstProcessingPoint.getId());

        List<OrderProcessingPointDto> actualProcessingPoints = orderProcessingPointService.findAllOrderProcessingPoints();

        Assertions.assertEquals(Arrays.asList(secondProcessingPoint, thirdProcessingPoint)
                , actualProcessingPoints);
    }

    @Test
    void findAllOrderProcessingPoints() throws SQLException {
        OrderProcessingPointDto firstProcessingPoint = new OrderProcessingPointDto();
        OrderProcessingPointDto secondProcessingPoint = new OrderProcessingPointDto();
        OrderProcessingPointDto thirdProcessingPoint = new OrderProcessingPointDto();

        orderProcessingPointService.addOrderProcessingPoint(firstProcessingPoint);
        orderProcessingPointService.addOrderProcessingPoint(secondProcessingPoint);
        orderProcessingPointService.addOrderProcessingPoint(thirdProcessingPoint);

        List<OrderProcessingPointDto> actualOrderProcessingPoints = orderProcessingPointService.findAllOrderProcessingPoints();

        Assertions.assertEquals(Arrays.asList(firstProcessingPoint, secondProcessingPoint, thirdProcessingPoint)
                , actualOrderProcessingPoints);
    }

    @SneakyThrows
    @Test
    void getOrderProcessingPoint() {
        OrderProcessingPointDto processingPoint = new OrderProcessingPointDto();
        processingPoint.setId(1L);
        processingPoint.setLocation("Minsk");

        OrderProcessingPointDto expected = new OrderProcessingPointDto();
        expected.setId(2L);
        expected.setLocation("Moscow");

        orderProcessingPointService.addOrderProcessingPoint(processingPoint);
        orderProcessingPointService.addOrderProcessingPoint(expected);


        OrderProcessingPointDto actual = orderProcessingPointService.getOrderProcessingPoint(2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() throws SQLException {
        OrderProcessingPointDto expected = new OrderProcessingPointDto();
        expected.setId(1L);
        expected.setLocation("Minsk");

        orderProcessingPointService.addOrderProcessingPoint(expected);

        String newLocation = "Homel";

        expected.setLocation(newLocation);

        OrderProcessingPointDto actual = orderProcessingPointService.update(expected);

        Assertions.assertEquals(expected, actual);
    }
}