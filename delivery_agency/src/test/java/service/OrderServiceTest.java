package service;

import dto.*;
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
import java.util.*;
import java.util.stream.Stream;

import static entity.OrderStates.READY_TO_SEND;

class OrderServiceTest {
    private final OrderService orderService = new OrderServiceImpl();

    private static Stream<Arguments> testDataForCalculate() {
        OrderProcessingPointDto processingPointToTest = new OrderProcessingPointDto();
        processingPointToTest.setLocation("Russia");
        processingPointToTest.setWarehouse(new WarehouseDto());
        OrderDto firstOrderToTest = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build()
                )
                .sender(ClientDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .recipient(ClientDto.builder().build())
                .destinationPlace(processingPointToTest)
                .state(OrderStateDto.builder().build())
                .history(OrderHistoryDto.builder().user(UserDto.builder().build()).build())
                .build();

        processingPointToTest.setLocation("Poland");
        processingPointToTest.setId(2L);
        OrderDto secondOrderToTest = OrderDto.builder()
                .id(2L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(4.0)
                        .width(10.0)
                        .length(1.0)
                        .weight(20.0).build()
                )
                .destinationPlace(processingPointToTest)
                .sender(ClientDto.builder().build())
                .recipient(ClientDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .state(OrderStateDto.builder().build())
                .history(OrderHistoryDto.builder().user(UserDto.builder().build()).build())
                .build();
        return Stream.of(
                Arguments.of(firstOrderToTest, BigDecimal.valueOf(72.0)),
                Arguments.of(secondOrderToTest, BigDecimal.valueOf(108.0))
        );
    }

    @Test
    void updateOrderCurrentLocation() throws SQLException {
        OrderProcessingPointDto orderProcessingPoint = new OrderProcessingPointDto();
        orderProcessingPoint.setLocation("Russia");
        orderProcessingPoint.setWarehouse(new WarehouseDto());
        OrderDto expected = OrderDto.builder()
                .id(1L)
                .parcelParameters(
                        ParcelParametersDto.builder()
                                .height(1.0)
                                .width(1.0)
                                .length(1.0)
                                .weight(20.0).build()
                )
                .sender(ClientDto.builder().build())
                .recipient(ClientDto.builder().build())
                .destinationPlace(orderProcessingPoint)
                .price(BigDecimal.valueOf(1))
                .state(OrderStateDto.builder().build())
                .history(OrderHistoryDto.builder().user(UserDto.builder().build()).build())
                .build();

        orderService.create(expected);

        orderProcessingPoint.setLocation("Poland");

        orderService.updateOrderCurrentLocation(expected.getId(), orderProcessingPoint);

        OrderDto actual = orderService.findById(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateOrderHistory() throws SQLException {
        OrderProcessingPointDto orderProcessingPoint = new OrderProcessingPointDto();
        orderProcessingPoint.setLocation("Russia");
        orderProcessingPoint.setWarehouse(new WarehouseDto());
        OrderHistoryDto expected = OrderHistoryDto.builder().user(UserDto.builder().build()).build();
        GregorianCalendar changingTime = new GregorianCalendar();
        changingTime.set(Calendar.HOUR_OF_DAY, 15);
        changingTime.set(Calendar.MINUTE, 35);
        expected.setChangingTime(changingTime);
        OrderDto order = OrderDto.builder()
                .id(1L)
                .history(expected)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(orderProcessingPoint)
                .sender(ClientDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .state(OrderStateDto.builder().build())
                .recipient(ClientDto.builder().build())
                .build();

        orderService.create(order);

        OrderHistoryDto newOrderHistory = OrderHistoryDto.builder().user(UserDto.builder().build()).build();

        changingTime.set(Calendar.HOUR_OF_DAY, 12);

        newOrderHistory.setChangingTime(changingTime);

        orderService.updateOrderHistory(1, newOrderHistory);

        OrderHistoryDto actual = orderService.findById(1).getHistory();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void create() throws SQLException {
        OrderProcessingPointDto orderProcessingPoint = new OrderProcessingPointDto();
        orderProcessingPoint.setLocation("Russia");
        orderProcessingPoint.setWarehouse(new WarehouseDto());
        orderProcessingPoint.setWarehouse(new WarehouseDto());
        OrderDto expectedOrder = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(orderProcessingPoint)
                .sender(ClientDto.builder().build())
                .recipient(ClientDto.builder().build())
                .price(BigDecimal.valueOf(64.0))
                .state(OrderStateDto.builder()
                        .state("READY_TO_SEND")
                        .build())
                .build();

        expectedOrder.setHistory(OrderHistoryDto.builder()
                .user(UserDto.builder().build())
                .build());
        orderService.create(expectedOrder);

        OrderDto actualOrder = orderService.findById(1);

        Assertions.assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void findById() throws SQLException {
        OrderProcessingPointDto orderProcessingPoint = new OrderProcessingPointDto();
        orderProcessingPoint.setLocation("Russia");
        orderProcessingPoint.setWarehouse(new WarehouseDto());
        OrderDto expectedOrder = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(orderProcessingPoint)
                .sender(ClientDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .recipient(ClientDto.builder().build())
                .state(OrderStateDto.builder().build())
                .history(OrderHistoryDto.builder().user(UserDto.builder().build()).build())
                .build();

        orderService.create(expectedOrder);

        OrderDto actualOrder = orderService.findById(1);

        Assertions.assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void findByRecipient() throws SQLException {
        ClientDto recipient = ClientDto.builder()
                .id(1L)
                .name("Igor")
                .build();

        OrderProcessingPointDto orderProcessingPoint = new OrderProcessingPointDto();
        orderProcessingPoint.setLocation("Russia");
        orderProcessingPoint.setWarehouse(new WarehouseDto());

        OrderDto expectedOrder = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(orderProcessingPoint)
                .sender(ClientDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .recipient(recipient)
                .state(OrderStateDto.builder().build())
                .history(OrderHistoryDto.builder().user(UserDto.builder().build()).build())
                .build();

        orderService.create(expectedOrder);

        OrderDto actualOrder = orderService.findByRecipient(recipient);

        Assertions.assertEquals(expectedOrder, actualOrder);
    }

    @SneakyThrows
    @Test
    void findBySender() {
        ClientDto sender = ClientDto.builder()
                .id(1L)
                .name("Igor")
                .build();

        OrderProcessingPointDto orderProcessingPoint = new OrderProcessingPointDto();
        orderProcessingPoint.setLocation("Russia");
        orderProcessingPoint.setWarehouse(new WarehouseDto());

        OrderDto expectedOrder = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(orderProcessingPoint)
                .recipient(sender)
                .price(BigDecimal.valueOf(1))
                .sender(ClientDto.builder().build())
                .state(OrderStateDto.builder().build())
                .history(OrderHistoryDto.builder().user(UserDto.builder().build()).build())
                .build();

        orderService.create(expectedOrder);

        OrderDto actualOrder = orderService.findByRecipient(sender);

        Assertions.assertEquals(expectedOrder, actualOrder);

    }

    @Test
    void send() throws SQLException {
        OrderProcessingPointDto orderProcessingPoint = new OrderProcessingPointDto();
        orderProcessingPoint.setId(1L);
        orderProcessingPoint.setLocation("Russia");
        orderProcessingPoint.setWarehouse(new WarehouseDto());

        OrderDto order = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(orderProcessingPoint)
                .price(BigDecimal.valueOf(1))
                .recipient(ClientDto.builder().build())
                .sender(ClientDto.builder().build())
                .state(OrderStateDto.builder().build())
                .history(OrderHistoryDto.builder().user(UserDto.builder().build()).build())
                .build();
        List<OrderDto> expectedOrders = Collections.singletonList(
                order
        );

        orderService.create(order);

        orderService.send(expectedOrders);

        List<List<OrderDto>> ordersOnTheWay = orderService.getOrdersOnTheWay();

        List<OrderDto> actualOrders = ordersOnTheWay.get(0);
        Assertions.assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void getState() throws SQLException {
        OrderProcessingPointDto orderProcessingPoint = new OrderProcessingPointDto();
        orderProcessingPoint.setId(1L);
        orderProcessingPoint.setLocation("Russia");
        orderProcessingPoint.setWarehouse(new WarehouseDto());

        OrderDto order = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(orderProcessingPoint)
                .currentLocation(orderProcessingPoint)
                .price(BigDecimal.valueOf(1))
                .recipient(ClientDto.builder().build())
                .sender(ClientDto.builder().build())
                .state(OrderStateDto.builder().build())
                .history(OrderHistoryDto.builder().user(UserDto.builder().build()).build())
                .build();

        orderService.create(order);

        String actual = orderService.getState(1);

        String expected = "READY_TO_SEND";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareOrders() throws SQLException {
        OrderProcessingPointDto firstProcessingPoint = new OrderProcessingPointDto();
        firstProcessingPoint.setId(1L);
        firstProcessingPoint.setLocation("Russia");
        firstProcessingPoint.setWarehouse(new WarehouseDto());

        OrderProcessingPointDto secondProcessingPoint = new OrderProcessingPointDto();
        secondProcessingPoint.setId(2L);
        secondProcessingPoint.setLocation("Russia");
        secondProcessingPoint.setWarehouse(new WarehouseDto());

        OrderDto firstOrder = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .recipient(ClientDto.builder().build())
                .sender(ClientDto.builder().build())
                .destinationPlace(firstProcessingPoint)
                .price(BigDecimal.valueOf(1))
                .currentLocation(firstProcessingPoint)
                .state(OrderStateDto.builder().build())
                .history(OrderHistoryDto.builder().user(UserDto.builder().build()).build())
                .recipient(ClientDto.builder().build())
                .build();

        OrderDto secondOrder = OrderDto.builder()
                .id(2L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(secondProcessingPoint)
                .currentLocation(secondProcessingPoint)
                .sender(ClientDto.builder().build())
                .recipient(ClientDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .state(OrderStateDto.builder().build())
                .history(OrderHistoryDto.builder().user(UserDto.builder().build()).build())
                .recipient(ClientDto.builder().build())
                .build();

        orderService.create(firstOrder);
        orderService.create(secondOrder);

        List<OrderDto> actual = Arrays.asList(
                firstOrder, secondOrder
        );

        orderService.send(actual);

        List<List<OrderDto>> ordersOnTheWay = orderService.getOrdersOnTheWay();

        actual.get(0).setId(5L);

        List<OrderDto> expected = ordersOnTheWay.get(0);

        Assertions.assertNotEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("testDataForCalculate")
    void calculatePrice(OrderDto order, BigDecimal expectedPrice) throws SQLException {
        BigDecimal actualPrice = orderService.calculatePrice(order);

        Assertions.assertEquals(expectedPrice.doubleValue(), actualPrice.doubleValue(), 0.001);
    }
}