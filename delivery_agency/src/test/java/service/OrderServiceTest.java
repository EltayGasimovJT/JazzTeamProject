package service;

import dto.OrderDto;
import entity.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.OrderServiceImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Stream;

class OrderServiceTest {
    private final OrderService orderService = new OrderServiceImpl();

    private static Stream<Arguments> testDataForCalculate() {
        OrderProcessingPoint processingPointToTest = new OrderProcessingPoint();
        processingPointToTest.setLocation("Russia");
        OrderDto firstOrderToTest = OrderDto.builder()
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
                .destinationPlace(processingPointToTest)
                .build();

        processingPointToTest.setLocation("Poland");
        processingPointToTest.setId(2L);
        OrderDto secondOrderToTest = OrderDto.builder()
                .id(2L)
                .parcelParameters(ParcelParameters.builder()
                        .height(4)
                        .width(10)
                        .length(1)
                        .weight(20).build()
                )
                .destinationPlace(processingPointToTest)
                .sender(Client.builder().build())
                .recipient(Client.builder().build())
                .price(BigDecimal.valueOf(1))
                .build();
        return Stream.of(
                Arguments.of(firstOrderToTest, BigDecimal.valueOf(72.0)),
                Arguments.of(secondOrderToTest, BigDecimal.valueOf(108.0))
        );
    }

    @Test
    void updateOrderCurrentLocation() throws SQLException {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setLocation("Russia");
        OrderDto expected = OrderDto.builder()
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

        orderService.create(expected);

        orderProcessingPoint.setLocation("Poland");

        orderService.updateOrderCurrentLocation(expected.getId(), orderProcessingPoint);

        OrderDto actual = orderService.findById(1);

        Assertions.assertNotEquals(expected, actual);
    }

    @Test
    void updateOrderHistory() throws SQLException {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setLocation("Russia");
        OrderHistory expected = OrderHistory.builder().build();
        GregorianCalendar changingTime = new GregorianCalendar();
        changingTime.set(Calendar.HOUR_OF_DAY, 15);
        changingTime.set(Calendar.MINUTE, 35);
        expected.setChangingTime(changingTime);
        OrderDto order = OrderDto.builder()
                .id(1L)
                .history(expected)
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

        OrderHistory newOrderHistory = OrderHistory.builder().build();

        changingTime.set(Calendar.HOUR_OF_DAY, 12);

        newOrderHistory.setChangingTime(changingTime);

        orderService.updateOrderHistory(1, newOrderHistory);

        OrderHistory actual = orderService.findById(1).getHistory();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void create() throws SQLException {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setLocation("Russia");
        OrderDto expectedOrder = OrderDto.builder()
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

        orderService.create(expectedOrder);

        OrderDto actualOrder = orderService.findById(1);

        Assertions.assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void findById() throws SQLException {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setLocation("Russia");
        OrderDto expectedOrder = OrderDto.builder()
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

        orderService.create(expectedOrder);

        OrderDto actualOrder = orderService.findById(1);

        Assertions.assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void findByRecipient() throws SQLException {
        Client recipient = Client.builder()
                .id(1L)
                .name("Igor")
                .build();

        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setLocation("Russia");

        OrderDto expectedOrder = OrderDto.builder()
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

        orderService.create(expectedOrder);

        OrderDto actualOrder = orderService.findByRecipient(recipient);

        Assertions.assertEquals(expectedOrder, actualOrder);
    }

    @SneakyThrows
    @Test
    void findBySender() {
        Client sender = Client.builder()
                .id(1L)
                .name("Igor")
                .build();

        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setLocation("Russia");

        OrderDto expectedOrder = OrderDto.builder()
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

        orderService.create(expectedOrder);

        OrderDto actualOrder = orderService.findByRecipient(sender);

        Assertions.assertEquals(expectedOrder, actualOrder);

    }

    @Test
    void getCurrentOrderLocation() throws SQLException {
        OrderProcessingPoint expectedLocation = new OrderProcessingPoint();
        expectedLocation.setId(1L);
        expectedLocation.setLocation("Russia");

        OrderDto order = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParameters.builder()
                        .height(1)
                        .width(1)
                        .length(1)
                        .weight(20).build())
                .sender(Client.builder().build())
                .recipient(Client.builder().build())
                .price(BigDecimal.valueOf(1))
                .destinationPlace(expectedLocation)
                .currentLocation(expectedLocation)
                .build();
        orderService.create(order);


        AbstractLocation actualLocation = orderService.getCurrentOrderLocation(order.getId());

        Assertions.assertEquals(expectedLocation, actualLocation);
    }

    @Test
    void send() throws SQLException {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setId(1L);
        orderProcessingPoint.setLocation("Russia");

        OrderDto order = OrderDto.builder()
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
        List<OrderDto> expectedOrders = Arrays.asList(
                order
        );

        orderService.create(order);

        orderService.send(expectedOrders, voyage);

        List<List<OrderDto>> ordersOnTheWay = orderService.getOrdersOnTheWay();

        List<OrderDto> actualOrders = ordersOnTheWay.get(0);
        Assertions.assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void getState() throws SQLException {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setId(1L);
        orderProcessingPoint.setLocation("Russia");

        OrderDto order = OrderDto.builder()
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

        String actual = orderService.getState(1);

        String expected = "Ready To Send";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareOrders() throws SQLException {
        OrderProcessingPoint firstProcessingPoint = new OrderProcessingPoint();
        firstProcessingPoint.setId(1L);
        firstProcessingPoint.setLocation("Russia");

        OrderProcessingPoint secondProcessingPoint = new OrderProcessingPoint();
        secondProcessingPoint.setId(2L);
        secondProcessingPoint.setLocation("Russia");

        OrderDto firstOrder = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParameters.builder()
                        .height(1)
                        .width(1)
                        .length(1)
                        .weight(20).build())
                .recipient(Client.builder().build())
                .sender(Client.builder().build())
                .destinationPlace(firstProcessingPoint)
                .price(BigDecimal.valueOf(1))
                .currentLocation(firstProcessingPoint)
                .build();

        OrderDto secondOrder = OrderDto.builder()
                .id(2L)
                .parcelParameters(ParcelParameters.builder()
                        .height(1)
                        .width(1)
                        .length(1)
                        .weight(20).build())
                .destinationPlace(secondProcessingPoint)
                .currentLocation(secondProcessingPoint)
                .sender(Client.builder().build())
                .recipient(Client.builder().build())
                .price(BigDecimal.valueOf(1))
                .build();

        orderService.create(firstOrder);
        orderService.create(secondOrder);

        List<OrderDto> actual = Arrays.asList(
                firstOrder, secondOrder
        );

        orderService.send(actual, new Voyage());

        List<List<OrderDto>> ordersOnTheWay = orderService.getOrdersOnTheWay();

        actual.get(0).setId(5L);

        List<OrderDto> expected = ordersOnTheWay.get(0);

        Assertions.assertNotEquals(expected, actual);
    }

    @Test
    void isFinalWarehouse() {
        OrderProcessingPoint processingPoint = new OrderProcessingPoint();
        processingPoint.setId(1L);
        processingPoint.setLocation("Moscow");
        OrderDto order = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParameters.builder()
                        .height(1)
                        .width(1)
                        .length(1)
                        .weight(20).build())
                .sender(Client.builder().build())
                .recipient(Client.builder().build())
                .destinationPlace(processingPoint)
                .price(BigDecimal.valueOf(1))
                .currentLocation(processingPoint)
                .build();
        Assertions.assertTrue(orderService.isFinalWarehouse(order));
    }


    @ParameterizedTest
    @MethodSource("testDataForCalculate")
    void calculatePrice(OrderDto order, BigDecimal expectedPrice) throws SQLException {
        BigDecimal actualPrice = orderService.calculatePrice(order);

        Assertions.assertEquals(expectedPrice.doubleValue(), actualPrice.doubleValue(), 0.001);
    }
}