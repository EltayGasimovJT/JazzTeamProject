package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import entity.Order;
import entity.OrderHistory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.modelmapper.ModelMapper;
import service.impl.OrderServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class OrderServiceTest {
    private final OrderService orderService = new OrderServiceImpl();
    private final ModelMapper modelMapper = new ModelMapper();
    private final ObjectMapper objectMapper = new ObjectMapper();

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
                .currentLocation(new OrderProcessingPointDto())
                .destinationPlace(processingPointToTest)
                .state(OrderStateDto.builder().build())
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
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
                .currentLocation(new OrderProcessingPointDto())
                .price(BigDecimal.valueOf(1))
                .state(OrderStateDto.builder().build())
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
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
        OrderDto expectedDto = OrderDto.builder()
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
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
                .currentLocation(new OrderProcessingPointDto())
                .build();

        orderService.create(expectedDto);

        orderProcessingPoint.setLocation("Poland");

        orderService.updateOrderCurrentLocation(expectedDto.getId(), orderProcessingPoint);

        Order actual = orderService.findOne(1);

        OrderDto actualDto = modelMapper.map(actual, OrderDto.class);

        Assertions.assertEquals(expectedDto, actualDto);
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
                .history(Collections.singletonList(expected))
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

        List<OrderHistory> actual = orderService.findOne(1).getHistory();

        List<OrderHistoryDto> actualHistory = actual.stream()
                .map(orderHistory -> modelMapper.map(orderHistory, OrderHistoryDto.class))
                .collect(Collectors.toList());

        Assertions.assertEquals(expected, actualHistory.get(0));
    }

    @Test
    void create() throws SQLException, IOException {
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
                .currentLocation(new OrderProcessingPointDto())
                .price(BigDecimal.valueOf(64.0))
                .state(OrderStateDto.builder()
                        .state("READY_TO_SEND")
                        .build())
                .history(Collections.singletonList(OrderHistoryDto.builder().build()))
                .build();

        expectedOrder.setHistory(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()));
        orderService.create(expectedOrder);

        Order actualOrder = orderService.findOne(1);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        OrderDto actualOrderDto = objectMapper.readValue(objectMapper.writeValueAsBytes(actualOrder), OrderDto.class);

        Assertions.assertEquals(expectedOrder, actualOrderDto);
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
                .currentLocation(new OrderProcessingPointDto())
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
                .build();

        orderService.create(expectedOrder);

        Order actualOrder = orderService.findOne(1);

        OrderDto actualOrderDto = modelMapper.map(actualOrder, OrderDto.class);

        Assertions.assertEquals(expectedOrder, actualOrderDto);
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
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
                .build();

        orderService.create(expectedOrder);

        Order actualOrder = orderService.findByRecipient(recipient);

        OrderDto actualOrderDto = modelMapper.map(actualOrder, OrderDto.class);

        Assertions.assertEquals(expectedOrder, actualOrderDto);
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
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
                .build();

        orderService.create(expectedOrder);

        Order actualOrder = orderService.findByRecipient(sender);

        OrderDto actualOrderDto = modelMapper.map(actualOrder, OrderDto.class);

        Assertions.assertEquals(expectedOrder, actualOrderDto);

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
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
                .build();
        List<OrderDto> expectedOrders = Collections.singletonList(
                order
        );

        orderService.create(order);

        orderService.send(expectedOrders);

        List<List<Order>> ordersOnTheWay = orderService.getOrdersOnTheWay();

        List<Order> actualOrders = ordersOnTheWay.get(0);

        final List<OrderDto> actualOrderDtos = actualOrders.stream()
                .map(actualOrder -> modelMapper.map(actualOrder, OrderDto.class))
                .collect(Collectors.toList());

        Assertions.assertEquals(expectedOrders, actualOrderDtos);
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
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
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
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
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
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
                .recipient(ClientDto.builder().build())
                .build();

        orderService.create(firstOrder);
        orderService.create(secondOrder);

        List<OrderDto> actual = Arrays.asList(
                firstOrder, secondOrder
        );

        orderService.send(actual);

        List<List<Order>> ordersOnTheWay = orderService.getOrdersOnTheWay();

        actual.get(0).setId(5L);

        List<Order> expected = ordersOnTheWay.get(0);

        final List<OrderDto> actualOrderDtos = expected.stream()
                .map(actualOrder -> modelMapper.map(actualOrder, OrderDto.class))
                .collect(Collectors.toList());

        Assertions.assertNotEquals(expected, actualOrderDtos);
    }

    @ParameterizedTest
    @MethodSource("testDataForCalculate")
    void calculatePrice(OrderDto order, BigDecimal expectedPrice) throws SQLException {
        BigDecimal actualPrice = orderService.calculatePrice(order);

        Assertions.assertEquals(expectedPrice.doubleValue(), actualPrice.doubleValue(), 0.001);
    }
}
