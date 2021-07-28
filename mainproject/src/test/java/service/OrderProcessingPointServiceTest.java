package service;

import entity.OrderProcessingPoint;
import entity.Warehouse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import service.impl.OrderProcessingPointServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class OrderProcessingPointServiceTest {
    private final OrderProcessingPointService orderProcessingPointService = new OrderProcessingPointServiceImpl();

    @Test
    void addOrderProcessingPoint() {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setId(1);
        orderProcessingPoint.setLocation("Minsk");
        orderProcessingPoint.setWarehouse(new Warehouse());

        orderProcessingPointService.addOrderProcessingPoint(orderProcessingPoint);

        String actualLocation = orderProcessingPointService.getOrderProcessingPoint(orderProcessingPoint.getId()).getLocation();
        Assert.assertEquals("Minsk", actualLocation);
    }

    @Test
    void deleteOrderProcessingPoint() {
    }

    @Test
    void findAllOrderProcessingPoints() {
    }

    @Test
    void getOrderProcessingPoint() {
    }

    @Test
    void update() {
    }
}