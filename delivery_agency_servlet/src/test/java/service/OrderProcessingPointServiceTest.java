package service;

import entity.OrderProcessingPoint;
import entity.Warehouse;
import lombok.SneakyThrows;
import org.junit.Assert;
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
        OrderProcessingPoint firstProcessingPointToTest = new OrderProcessingPoint();
        firstProcessingPointToTest.setId(1L);
        firstProcessingPointToTest.setLocation("Minsk");
        firstProcessingPointToTest.setWarehouse(new Warehouse());

        OrderProcessingPoint secondProcessingPointToTest = new OrderProcessingPoint();
        secondProcessingPointToTest.setId(2L);
        secondProcessingPointToTest.setLocation("Moscow");
        secondProcessingPointToTest.setWarehouse(new Warehouse());

        OrderProcessingPoint thirdProcessingPointToTest = new OrderProcessingPoint();
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
    void addOrderProcessingPoint(OrderProcessingPoint orderProcessingPoint, String expectedLocation) {
        orderProcessingPointService.addOrderProcessingPoint(orderProcessingPoint);

        String actualLocation = orderProcessingPointService.getOrderProcessingPoint(orderProcessingPoint.getId()).getLocation();
        Assert.assertEquals(expectedLocation, actualLocation);
    }

    @Test
    void deleteOrderProcessingPoint() throws SQLException {
        OrderProcessingPoint firstProcessingPoint = new OrderProcessingPoint();
        firstProcessingPoint.setId(1L);

        OrderProcessingPoint secondProcessingPoint = new OrderProcessingPoint();
        secondProcessingPoint.setId(2L);

        OrderProcessingPoint thirdProcessingPoint = new OrderProcessingPoint();
        thirdProcessingPoint.setId(3L);

        orderProcessingPointService.addOrderProcessingPoint(firstProcessingPoint);
        orderProcessingPointService.addOrderProcessingPoint(secondProcessingPoint);
        orderProcessingPointService.addOrderProcessingPoint(thirdProcessingPoint);

        orderProcessingPointService.deleteOrderProcessingPoint(thirdProcessingPoint);

        List<OrderProcessingPoint> actualProcessingPoints = orderProcessingPointService.findAllOrderProcessingPoints();
        Assert.assertEquals(Arrays.asList(firstProcessingPoint, secondProcessingPoint)
                , actualProcessingPoints);
    }

    @Test
    void findAllOrderProcessingPoints() throws SQLException {
        OrderProcessingPoint firstProcessingPoint = new OrderProcessingPoint();
        OrderProcessingPoint secondProcessingPoint = new OrderProcessingPoint();
        OrderProcessingPoint thirdProcessingPoint = new OrderProcessingPoint();

        orderProcessingPointService.addOrderProcessingPoint(firstProcessingPoint);
        orderProcessingPointService.addOrderProcessingPoint(secondProcessingPoint);
        orderProcessingPointService.addOrderProcessingPoint(thirdProcessingPoint);

        List<OrderProcessingPoint> actualOrderProcessingPoints = orderProcessingPointService.findAllOrderProcessingPoints();

        Assert.assertEquals(Arrays.asList(firstProcessingPoint, secondProcessingPoint, thirdProcessingPoint)
                , actualOrderProcessingPoints);
    }

    @SneakyThrows
    @Test
    void getOrderProcessingPoint() {
        OrderProcessingPoint firstProcessingPoint = new OrderProcessingPoint();
        firstProcessingPoint.setId(1L);
        firstProcessingPoint.setLocation("Minsk");

        OrderProcessingPoint expected = new OrderProcessingPoint();
        expected.setId(2L);
        expected.setLocation("Moscow");

        orderProcessingPointService.addOrderProcessingPoint(firstProcessingPoint);
        orderProcessingPointService.addOrderProcessingPoint(expected);


        OrderProcessingPoint actual = orderProcessingPointService.getOrderProcessingPoint(2);

        Assert.assertEquals(expected, actual);
    }

    @Test
    void update() throws SQLException {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setId(1L);
        orderProcessingPoint.setLocation("Minsk");

        orderProcessingPointService.addOrderProcessingPoint(orderProcessingPoint);

        String expectedLocation = "Homel";

        orderProcessingPoint.setLocation(expectedLocation);

        String actualLocation = orderProcessingPointService.getOrderProcessingPoint(1).getLocation();

        Assert.assertEquals(expectedLocation, actualLocation);
    }
}