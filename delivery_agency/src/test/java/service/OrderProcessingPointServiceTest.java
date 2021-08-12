package service;

import dto.OrderProcessingPointDto;
import entity.OrderProcessingPoint;
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
        OrderProcessingPointDto firstProcessingPointToTest = OrderProcessingPointDto.builder()
                .id(1L)
                .location("Minsk")
                .warehouse(new Warehouse())
                .build();

        OrderProcessingPointDto secondProcessingPointToTest = OrderProcessingPointDto.builder()
                .id(2L)
                .location("Moscow")
                .warehouse(new Warehouse())
                .build();

        OrderProcessingPointDto thirdProcessingPointToTest = OrderProcessingPointDto.builder()
                .id(3L)
                .location("Grodno")
                .warehouse(new Warehouse())
                .build();

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
        OrderProcessingPointDto firstProcessingPoint = OrderProcessingPointDto.builder()
                .id(1L)
                .build();

        OrderProcessingPointDto secondProcessingPoint  = OrderProcessingPointDto.builder()
                .id(2L)
                .build();

        OrderProcessingPointDto thirdProcessingPoint  = OrderProcessingPointDto.builder()
                .id(3L)
                .build();

        orderProcessingPointService.addOrderProcessingPoint(firstProcessingPoint);
        orderProcessingPointService.addOrderProcessingPoint(secondProcessingPoint);
        orderProcessingPointService.addOrderProcessingPoint(thirdProcessingPoint);

        orderProcessingPointService.deleteOrderProcessingPoint(thirdProcessingPoint);

        List<OrderProcessingPointDto> actualProcessingPoints = orderProcessingPointService.findAllOrderProcessingPoints();
        Assertions.assertEquals(Arrays.asList(firstProcessingPoint, secondProcessingPoint)
                , actualProcessingPoints);
    }

    @Test
    void findAllOrderProcessingPoints() throws SQLException {
        OrderProcessingPointDto firstProcessingPoint = OrderProcessingPointDto.builder().build();
        OrderProcessingPointDto secondProcessingPoint = OrderProcessingPointDto.builder().build();
        OrderProcessingPointDto thirdProcessingPoint = OrderProcessingPointDto.builder().build();

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
        OrderProcessingPointDto processingPoint = OrderProcessingPointDto.builder()
                .id(1L)
                .location("Minsk")
                .build();

        OrderProcessingPointDto expected = OrderProcessingPointDto.builder()
                .id(2L)
                .location("Moscow")
                .build();

        orderProcessingPointService.addOrderProcessingPoint(processingPoint);
        orderProcessingPointService.addOrderProcessingPoint(expected);


        OrderProcessingPointDto actual = orderProcessingPointService.getOrderProcessingPoint(2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() throws SQLException {
        OrderProcessingPointDto orderProcessingPoint = OrderProcessingPointDto.builder()
                .id(1L)
                .location("Minsk")
                .build();

        orderProcessingPointService.addOrderProcessingPoint(orderProcessingPoint);

        String expected = "Homel";

        orderProcessingPoint.setLocation(expected);

        String actualLocation = orderProcessingPointService.getOrderProcessingPoint(1).getLocation();

        Assertions.assertEquals(expected, actualLocation);
    }
}