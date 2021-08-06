package service;

import entity.OrderProcessingPoint;
import entity.Warehouse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.OrderProcessingPointServiceImpl;

import java.util.Arrays;
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

    @ParameterizedTest
    @MethodSource("testOrderProcessingPoints")
    void addOrderProcessingPoint(OrderProcessingPoint orderProcessingPoint, String expectedLocation) {
        orderProcessingPointService.addOrderProcessingPoint(orderProcessingPoint);

        String actualLocation = orderProcessingPointService.getOrderProcessingPoint(orderProcessingPoint.getId()).getLocation();
        Assert.assertEquals(expectedLocation, actualLocation);
    }

    @Test
    void deleteOrderProcessingPoint() {
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

        Assert.assertEquals(Arrays.asList(firstProcessingPoint, secondProcessingPoint)
                , orderProcessingPointService.findAllOrderProcessingPoints());
    }

    @Test
    void findAllOrderProcessingPoints() {
        OrderProcessingPoint firstProcessingPoint = new OrderProcessingPoint();
        OrderProcessingPoint secondProcessingPoint = new OrderProcessingPoint();
        OrderProcessingPoint thirdProcessingPoint = new OrderProcessingPoint();

        orderProcessingPointService.addOrderProcessingPoint(firstProcessingPoint);
        orderProcessingPointService.addOrderProcessingPoint(secondProcessingPoint);
        orderProcessingPointService.addOrderProcessingPoint(thirdProcessingPoint);

        Assert.assertEquals(Arrays.asList(firstProcessingPoint, secondProcessingPoint, thirdProcessingPoint)
                , orderProcessingPointService.findAllOrderProcessingPoints());
    }

    @Test
    void getOrderProcessingPoint() {
        OrderProcessingPoint firstProcessingPoint = new OrderProcessingPoint();
        firstProcessingPoint.setId(1L);
        firstProcessingPoint.setLocation("Minsk");

        OrderProcessingPoint secondProcessingPoint = new OrderProcessingPoint();
        secondProcessingPoint.setId(2L);
        secondProcessingPoint.setLocation("Moscow");

        orderProcessingPointService.addOrderProcessingPoint(firstProcessingPoint);
        orderProcessingPointService.addOrderProcessingPoint(secondProcessingPoint);


        OrderProcessingPoint orderProcessingPoint = orderProcessingPointService.getOrderProcessingPoint(2);

        Assert.assertEquals(secondProcessingPoint, orderProcessingPoint);
    }

    @Test
    void update() {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setId(1L);
        orderProcessingPoint.setLocation("Minsk");

        orderProcessingPointService.addOrderProcessingPoint(orderProcessingPoint);

        orderProcessingPoint.setLocation("Homel");

        OrderProcessingPoint processingPoint = orderProcessingPointService.getOrderProcessingPoint(1);

        Assert.assertEquals("Homel", processingPoint.getLocation());
    }
}