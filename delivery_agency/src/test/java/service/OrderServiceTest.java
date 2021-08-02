package service;


import entity.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.OrderServiceImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class OrderServiceTest {
    private final OrderService orderService = new OrderServiceImpl();

    private static Stream<Arguments> testDataForCalculate() {
        OrderProcessingPoint orderProcessingPoint1 = new OrderProcessingPoint();
        orderProcessingPoint1.setLocation("Russia");
        Order order1 = Order.builder()
                .id(1L)
                .parcelParameters(ParcelParameters.builder()
                        .height(1)
                        .width(1)
                        .length(1)
                        .weight(20).build()
                )
                .sender(Client.builder().build())
                .price(BigDecimal.valueOf(1))
                .recipient(Client.builder().build())
                .destinationPlace(orderProcessingPoint1)
                .build();

        orderProcessingPoint1.setLocation("Poland");
        orderProcessingPoint1.setId(2L);
        Order order2 = Order.builder()
                .id(2L)
                .parcelParameters(ParcelParameters.builder()
                        .height(4)
                        .width(10)
                        .length(1)
                        .weight(20).build()
                )
                .destinationPlace(orderProcessingPoint1)
                .sender(Client.builder().build())
                .recipient(Client.builder().build())
                .price(BigDecimal.valueOf(1))
                .build();
        return Stream.of(
                Arguments.of(order1, BigDecimal.valueOf(72.0)),
                Arguments.of(order2, BigDecimal.valueOf(108.0))
        );
    }

    @Test
    void updateOrderCurrentLocation() {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setLocation("Russia");
        Order order = Order.builder()
                .id(1L)
                .parcelParameters(
                        ParcelParameters.builder()
                                .height(1)
                                .width(1)
                                .length(1)
                                .weight(20).build()
                )
                .sender(Client.builder().build())
                .recipient(Client.builder().build())
                .destinationPlace(orderProcessingPoint)
                .price(BigDecimal.valueOf(1))
                .build();

        orderService.create(order);

        orderProcessingPoint.setLocation("Poland");

        orderService.updateOrderCurrentLocation(1, orderProcessingPoint);

        Assert.assertEquals(order, orderService.findById(1));
    }

    @Test
    void updateOrderHistory() {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setLocation("Russia");
        OrderHistory orderHistory = OrderHistory.builder().build();
        orderHistory.setChangingTime("14:33");
        Order order = Order.builder()
                .id(1L)
                .history(orderHistory)
                .parcelParameters(ParcelParameters.builder()
                        .height(1)
                        .width(1)
                        .length(1)
                        .weight(20).build())
                .destinationPlace(orderProcessingPoint)
                .sender(Client.builder().build())
                .price(BigDecimal.valueOf(1))
                .recipient(Client.builder().build())
                .build();

        orderService.addOrder(order);

        OrderHistory newOrderHistory = OrderHistory.builder().build();

        newOrderHistory.setChangingTime("12:35");

        orderService.updateOrderHistory(1, newOrderHistory);

        Assert.assertNotEquals(orderHistory, orderService.findById(1).getHistory());
    }

    @Test
    void create() {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setLocation("Russia");
        Order order = Order.builder()
                .id(1L)
                .parcelParameters(ParcelParameters.builder()
                        .height(1)
                        .width(1)
                        .length(1)
                        .weight(20).build())
                .destinationPlace(orderProcessingPoint)
                .sender(Client.builder().build())
                .recipient(Client.builder().build())
                .price(BigDecimal.valueOf(1))
                .build();

        orderService.create(order);

        Order byId = orderService.findById(1);

        Assert.assertEquals(order, byId);
    }

    @Test
    void findById() {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setLocation("Russia");
        Order order = Order.builder()
                .id(1L)
                .parcelParameters(ParcelParameters.builder()
                        .height(1)
                        .width(1)
                        .length(1)
                        .weight(20).build())
                .destinationPlace(orderProcessingPoint)
                .sender(Client.builder().build())
                .price(BigDecimal.valueOf(1))
                .recipient(Client.builder().build())
                .build();

        orderService.create(order);

        Order byId = orderService.findById(1);

        Assert.assertEquals(20, byId.getParcelParameters().getWeight(), 0.001);
    }

    @Test
    void findByRecipient() {
        Client recipient = Client.builder()
                .id(1L)
                .name("Igor")
                .build();

        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setLocation("Russia");

        Order order = Order.builder()
                .id(1L)
                .parcelParameters(ParcelParameters.builder()
                        .height(1)
                        .width(1)
                        .length(1)
                        .weight(20).build())
                .destinationPlace(orderProcessingPoint)
                .sender(Client.builder().build())
                .price(BigDecimal.valueOf(1))
                .recipient(recipient)
                .build();

        orderService.create(order);

        Order byRecipient = orderService.findByRecipient(recipient);

        Assert.assertEquals("Igor", byRecipient
                .getRecipient()
                .getName());
    }

    @Test
    void findBySender() {
        Client sender = Client.builder()
                .id(1L)
                .name("Igor")
                .build();

        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setLocation("Russia");

        Order order = Order.builder()
                .id(1L)
                .parcelParameters(ParcelParameters.builder()
                        .height(1)
                        .width(1)
                        .length(1)
                        .weight(20).build())
                .destinationPlace(orderProcessingPoint)
                .recipient(sender)
                .price(BigDecimal.valueOf(1))
                .sender(Client.builder().build())
                .build();

        orderService.create(order);

        Order bySender = orderService.findByRecipient(sender);

        Assert.assertEquals(bySender, order);

    }

    @Test
    void getCurrentOrderLocation() {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setId(1L);
        orderProcessingPoint.setLocation("Russia");

        Order order = Order.builder()
                .id(1L)
                .parcelParameters(ParcelParameters.builder()
                        .height(1)
                        .width(1)
                        .length(1)
                        .weight(20).build())
                .sender(Client.builder().build())
                .recipient(Client.builder().build())
                .price(BigDecimal.valueOf(1))
                .destinationPlace(orderProcessingPoint)
                .currentLocation(orderProcessingPoint)
                .build();
        orderService.create(order);


        AbstractLocation currentOrderLocation = orderService.getCurrentOrderLocation(order.getId());

        Assert.assertEquals(currentOrderLocation, orderProcessingPoint);
    }

    @Test
    void send() {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setId(1L);
        orderProcessingPoint.setLocation("Russia");

        Order order = Order.builder()
                .id(1L)
                .parcelParameters(ParcelParameters.builder()
                        .height(1)
                        .width(1)
                        .length(1)
                        .weight(20).build())
                .destinationPlace(orderProcessingPoint)
                .currentLocation(orderProcessingPoint)
                .price(BigDecimal.valueOf(1))
                .recipient(Client.builder().build())
                .sender(Client.builder().build())
                .build();
        Voyage voyage = new Voyage();
        List<Order> orders = Arrays.asList(
                order
        );

        orderService.create(order);

        orderService.send(orders, voyage);

        List<List<Order>> ordersOnTheWay = orderService.getOrdersOnTheWay();

        Assert.assertEquals(orders, ordersOnTheWay.get(0));
    }

    @Test
    void accept() {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setId(1L);
        orderProcessingPoint.setLocation("Russia");

        Order order = Order.builder()
                .id(1L)
                .parcelParameters(ParcelParameters.builder()
                        .height(1)
                        .width(1)
                        .length(1)
                        .weight(20).build())
                .destinationPlace(orderProcessingPoint)
                .currentLocation(orderProcessingPoint)
                .price(BigDecimal.valueOf(1))
                .sender(Client.builder().build())
                .recipient(Client.builder().build())
                .build();
        Voyage voyage = new Voyage();
        List<Order> orders = Arrays.asList(
                order
        );

        orderService.create(order);

        orderService.send(orders, voyage);

        orderService.accept(orders);

        List<List<Order>> ordersOnTheWay = orderService.getOrdersOnTheWay();

        Assert.assertEquals(0, ordersOnTheWay.size());
    }

    @Test
    void getState() {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setId(1L);
        orderProcessingPoint.setLocation("Russia");

        Order order = Order.builder()
                .id(1L)
                .parcelParameters(ParcelParameters.builder()
                        .height(1)
                        .width(1)
                        .length(1)
                        .weight(20).build())
                .destinationPlace(orderProcessingPoint)
                .currentLocation(orderProcessingPoint)
                .price(BigDecimal.valueOf(1))
                .recipient(Client.builder().build())
                .sender(Client.builder().build())
                .build();
        orderService.create(order);

        String state = orderService.getState(1);

        Assert.assertEquals("Ready To Send", state);
    }

    @Test
    void compareOrders() {
        OrderProcessingPoint orderProcessingPoint1 = new OrderProcessingPoint();
        orderProcessingPoint1.setId(1L);
        orderProcessingPoint1.setLocation("Russia");

        OrderProcessingPoint orderProcessingPoint2 = new OrderProcessingPoint();
        orderProcessingPoint2.setId(2L);
        orderProcessingPoint2.setLocation("Russia");

        Order order1 = Order.builder()
                .id(1L)
                .parcelParameters(ParcelParameters.builder()
                        .height(1)
                        .width(1)
                        .length(1)
                        .weight(20).build())
                .recipient(Client.builder().build())
                .sender(Client.builder().build())
                .destinationPlace(orderProcessingPoint1)
                .price(BigDecimal.valueOf(1))
                .currentLocation(orderProcessingPoint1)
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .parcelParameters(ParcelParameters.builder()
                        .height(1)
                        .width(1)
                        .length(1)
                        .weight(20).build())
                .destinationPlace(orderProcessingPoint2)
                .currentLocation(orderProcessingPoint2)
                .sender(Client.builder().build())
                .recipient(Client.builder().build())
                .price(BigDecimal.valueOf(1))
                .build();

        orderService.create(order1);
        orderService.create(order2);

        List<Order> ordersToSend = Arrays.asList(
                order1, order2
        );

        orderService.send(ordersToSend, new Voyage());

        List<List<Order>> ordersOnTheWay = orderService.getOrdersOnTheWay();

        ordersToSend.get(0).setId(5L);

        Assert.assertEquals(ordersOnTheWay.get(0), ordersToSend);
    }

    @Test
    void isFinalWarehouse() {
        OrderProcessingPoint orderProcessingPoint1 = new OrderProcessingPoint();
        orderProcessingPoint1.setId(1L);
        orderProcessingPoint1.setLocation("Moscow");
        Order order1 = Order.builder()
                .id(1L)
                .parcelParameters(ParcelParameters.builder()
                        .height(1)
                        .width(1)
                        .length(1)
                        .weight(20).build())
                .sender(Client.builder().build())
                .recipient(Client.builder().build())
                .destinationPlace(orderProcessingPoint1)
                .price(BigDecimal.valueOf(1))
                .currentLocation(orderProcessingPoint1)
                .build();
        Assert.assertTrue(orderService.isFinalWarehouse(order1));
    }


    @ParameterizedTest
    @MethodSource("testDataForCalculate")
    void calculatePrice(Order order, BigDecimal expectedPrice) {
        BigDecimal actualPrice = orderService.calculatePrice(order);

        Assert.assertEquals(expectedPrice.doubleValue(), actualPrice.doubleValue(), 0.001);
    }
}