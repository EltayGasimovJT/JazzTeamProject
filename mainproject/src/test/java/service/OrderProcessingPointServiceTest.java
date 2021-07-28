package service;

import entity.OrderProcessingPoint;
import entity.Warehouse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.OrderProcessingPointServiceImpl;

import java.util.stream.Stream;


class OrderProcessingPointServiceTest {
    private final OrderProcessingPointService orderProcessingPointService = new OrderProcessingPointServiceImpl();

    private static Stream<Arguments> testOrderProcessingPoints() {
        OrderProcessingPoint orderProcessingPoint1 = new OrderProcessingPoint();
        orderProcessingPoint1.setId(1);
        orderProcessingPoint1.setLocation("Minsk");
        orderProcessingPoint1.setWarehouse(new Warehouse());

        OrderProcessingPoint orderProcessingPoint2 = new OrderProcessingPoint();
        orderProcessingPoint2.setId(2);
        orderProcessingPoint2.setLocation("Moskov");
        orderProcessingPoint2.setWarehouse(new Warehouse());

        OrderProcessingPoint orderProcessingPoint3 = new OrderProcessingPoint();
        orderProcessingPoint3.setId(3);
        orderProcessingPoint3.setLocation("Grodno");
        orderProcessingPoint3.setWarehouse(new Warehouse());

        return Stream.of(
                Arguments.of(orderProcessingPoint1, "Minsk"),
                Arguments.of(orderProcessingPoint2, "Moskov"),
                Arguments.of(orderProcessingPoint3, "Grodno")
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
        OrderProcessingPoint orderProcessingPoint1 = new OrderProcessingPoint();
        orderProcessingPoint1.setId(1);

        OrderProcessingPoint orderProcessingPoint2 = new OrderProcessingPoint();
        orderProcessingPoint2.setId(2);

        OrderProcessingPoint orderProcessingPoint3 = new OrderProcessingPoint();
        orderProcessingPoint3.setId(3);

        orderProcessingPointService.addOrderProcessingPoint(orderProcessingPoint1);
        orderProcessingPointService.addOrderProcessingPoint(orderProcessingPoint2);
        orderProcessingPointService.addOrderProcessingPoint(orderProcessingPoint3);

        orderProcessingPointService.deleteOrderProcessingPoint(orderProcessingPoint3);

        Assert.assertEquals(2, orderProcessingPointService.findAllOrderProcessingPoints().size());
    }

    @Test
    void findAllOrderProcessingPoints() {
        OrderProcessingPoint orderProcessingPoint1 = new OrderProcessingPoint();
        orderProcessingPoint1.setId(1);

        OrderProcessingPoint orderProcessingPoint2 = new OrderProcessingPoint();
        orderProcessingPoint2.setId(2);

        OrderProcessingPoint orderProcessingPoint3 = new OrderProcessingPoint();
        orderProcessingPoint3.setId(3);

        orderProcessingPointService.addOrderProcessingPoint(orderProcessingPoint1);
        orderProcessingPointService.addOrderProcessingPoint(orderProcessingPoint2);
        orderProcessingPointService.addOrderProcessingPoint(orderProcessingPoint3);

        Assert.assertEquals(3, orderProcessingPointService.findAllOrderProcessingPoints().size());
    }

    @Test
    void getOrderProcessingPoint() {
        OrderProcessingPoint orderProcessingPoint1 = new OrderProcessingPoint();
        orderProcessingPoint1.setId(1);
        orderProcessingPoint1.setLocation("Minsk");

        OrderProcessingPoint orderProcessingPoint2 = new OrderProcessingPoint();
        orderProcessingPoint2.setId(2);
        orderProcessingPoint2.setLocation("Moskov");

        orderProcessingPointService.addOrderProcessingPoint(orderProcessingPoint1);
        orderProcessingPointService.addOrderProcessingPoint(orderProcessingPoint2);


        OrderProcessingPoint orderProcessingPoint = orderProcessingPointService.getOrderProcessingPoint(2);

        Assert.assertEquals("Moskov", orderProcessingPoint.getLocation());
    }

    @Test
    void update() {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setId(1);
        orderProcessingPoint.setLocation("Minsk");


    }
}