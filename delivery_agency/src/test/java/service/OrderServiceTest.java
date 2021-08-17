package service;

import dto.*;
import entity.Order;
import entity.OrderHistory;
import entity.WorkingPlaceType;
import lombok.SneakyThrows;
import mapping.CustomModelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.modelmapper.ModelMapper;
import service.impl.OrderServiceImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class OrderServiceTest {
    private final OrderService orderService = new OrderServiceImpl();
    private final ModelMapper modelMapper = new ModelMapper();

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
        OrderProcessingPointDto orderProcessingPointToTest = new OrderProcessingPointDto();
        orderProcessingPointToTest.setLocation("Russia");
        orderProcessingPointToTest.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);
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
                .destinationPlace(orderProcessingPointToTest)
                .price(BigDecimal.valueOf(1))
                .state(OrderStateDto.builder().build())
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
                .currentLocation(orderProcessingPointToTest)
                .build();

        orderService.save(expectedDto);

        orderProcessingPointToTest.setLocation("Poland");

        orderService.updateOrderCurrentLocation(expectedDto.getId(), orderProcessingPointToTest);

        Order actual = orderService.findOne(1);

        OrderDto actualDto = CustomModelMapper.mapDtoToOrder(actual);

        Assertions.assertEquals(expectedDto, actualDto);
    }

    @Test
    void updateOrderHistory() throws SQLException {
        OrderProcessingPointDto orderProcessingPointToTest = new OrderProcessingPointDto();
        orderProcessingPointToTest.setLocation("Russia");
        orderProcessingPointToTest.setWarehouse(new WarehouseDto());
        OrderHistoryDto expected = OrderHistoryDto.builder().build();
        GregorianCalendar changingTimeToTest = new GregorianCalendar();
        changingTimeToTest.set(Calendar.HOUR_OF_DAY, 15);
        changingTimeToTest.set(Calendar.MINUTE, 35);
        expected.setChangingTime(changingTimeToTest);
        OrderDto order = OrderDto.builder()
                .id(1L)
                .history(Collections.singletonList(expected))
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(orderProcessingPointToTest)
                .sender(ClientDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .currentLocation(new OrderProcessingPointDto())
                .state(OrderStateDto.builder().build())
                .recipient(ClientDto.builder().build())
                .build();

        orderService.save(order);

        OrderHistoryDto newOrderHistory = OrderHistoryDto.builder().user(UserDto.builder().build()).build();

        changingTimeToTest.set(Calendar.HOUR_OF_DAY, 12);

        newOrderHistory.setChangingTime(changingTimeToTest);

        orderService.updateOrderHistory(1, newOrderHistory);

        List<OrderHistory> actual = orderService.findOne(1).getHistory();

        List<OrderHistoryDto> actualHistory = actual.stream()
                .map(orderHistory -> modelMapper.map(orderHistory, OrderHistoryDto.class))
                .collect(Collectors.toList());

        Assertions.assertEquals(expected, actualHistory.get(0));
    }

    @Test
    void create() throws SQLException {
        AbstractBuildingDto currentLocationToTest = new OrderProcessingPointDto();
        currentLocationToTest.setId(1L);
        currentLocationToTest.setLocation("Russia");

        OrderProcessingPointDto destinationPlaceToTest = new OrderProcessingPointDto();
        destinationPlaceToTest.setId(2L);
        destinationPlaceToTest.setLocation("Russia");

        OrderDto expectedOrder = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(destinationPlaceToTest)
                .sender(ClientDto.builder()
                        .id(1L)
                        .passportId("125125")
                        .phoneNumber("125125")
                        .surname("qwtqtwqwt")
                        .name("qaawtawt")
                        .build()
                )
                .recipient(ClientDto.builder()
                        .id(2L)
                        .passportId("125125")
                        .phoneNumber("145125")
                        .surname("qwtqtwqwt")
                        .name("qaawtawt")
                        .build()
                )
                .currentLocation(currentLocationToTest)
                .price(BigDecimal.valueOf(64.0))
                .state(OrderStateDto.builder()
                        .state("READY_TO_SEND")
                        .rolesAllowedPutToState(Arrays.asList("Admin", "Pick up worker"))
                        .rolesAllowedWithdrawFromState(Arrays.asList("Admin", "Pick up worker"))
                        .build())
                .history(Collections.singletonList(OrderHistoryDto.builder().build()))
                .build();

        expectedOrder.setHistory(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()));

        orderService.save(expectedOrder);

        Order actualOrder = orderService.findOne(1);

        OrderDto actualOrderDto = CustomModelMapper.mapDtoToOrder(actualOrder);

        Assertions.assertEquals(expectedOrder, actualOrderDto);
    }

    @Test
    void findById() throws SQLException {
        OrderProcessingPointDto orderProcessingPointToTest = new OrderProcessingPointDto();
        orderProcessingPointToTest.setLocation("Russia");
        OrderDto expectedOrder = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(orderProcessingPointToTest)
                .sender(ClientDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .recipient(ClientDto.builder().build())
                .state(OrderStateDto.builder().build())
                .currentLocation(new OrderProcessingPointDto())
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
                .build();

        orderService.save(expectedOrder);

        Order actualOrder = orderService.findOne(1);

        OrderDto actualOrderDto = CustomModelMapper.mapDtoToOrder(actualOrder);

        Assertions.assertEquals(expectedOrder, actualOrderDto);
    }

    @Test
    void findByRecipient() throws SQLException {
        ClientDto recipientToTest = ClientDto.builder()
                .id(1L)
                .name("Igor")
                .build();

        OrderProcessingPointDto orderProcessingPointToTest = new OrderProcessingPointDto();
        orderProcessingPointToTest.setLocation("Russia");

        OrderDto expectedOrder = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(orderProcessingPointToTest)
                .sender(ClientDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .recipient(recipientToTest)
                .currentLocation(new OrderProcessingPointDto())
                .state(OrderStateDto.builder().build())
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
                .build();

        orderService.save(expectedOrder);

        Order actualOrder = orderService.findByRecipient(recipientToTest);

        OrderDto actualOrderDto = CustomModelMapper.mapDtoToOrder(actualOrder);

        Assertions.assertEquals(expectedOrder, actualOrderDto);
    }

    @SneakyThrows
    @Test
    void findBySender() {
        ClientDto senderToTest = ClientDto.builder()
                .id(1L)
                .name("Igor")
                .build();

        OrderProcessingPointDto orderProcessingPointToTest = new OrderProcessingPointDto();
        orderProcessingPointToTest.setLocation("Russia");

        OrderDto expectedOrder = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(orderProcessingPointToTest)
                .recipient(senderToTest)
                .currentLocation(orderProcessingPointToTest)
                .price(BigDecimal.valueOf(1))
                .sender(ClientDto.builder().build())
                .state(OrderStateDto.builder().build())
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
                .build();

        orderService.save(expectedOrder);

        Order actualOrder = orderService.findByRecipient(senderToTest);

        OrderDto actualOrderDto = CustomModelMapper.mapDtoToOrder(actualOrder);

        Assertions.assertEquals(expectedOrder, actualOrderDto);

    }

    @Test
    void send() throws SQLException {
        OrderProcessingPointDto orderProcessingPointToTest = new OrderProcessingPointDto();
        orderProcessingPointToTest.setId(1L);
        orderProcessingPointToTest.setLocation("Russia");

        OrderDto orderToTest = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(orderProcessingPointToTest)
                .price(BigDecimal.valueOf(1))
                .currentLocation(orderProcessingPointToTest)
                .recipient(ClientDto.builder().build())
                .sender(ClientDto.builder().build())
                .state(OrderStateDto.builder().build())
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
                .build();

        List<OrderDto> expectedOrders = Collections.singletonList(
                orderToTest
        );

        orderService.save(orderToTest);

        orderService.send(expectedOrders);

        List<List<Order>> ordersOnTheWay = orderService.getOrdersOnTheWay();

        List<Order> actualOrders = ordersOnTheWay.get(0);

        final List<OrderDto> actualOrderDtos = actualOrders.stream()
                .map(CustomModelMapper::mapDtoToOrder)
                .collect(Collectors.toList());

        String actualOrderState = actualOrderDtos.get(0).getState().getState();
        String expectedOrderState = "ON_THE_WAY_TO_THE_RECEPTION";

        Assertions.assertEquals(expectedOrderState, actualOrderState);
    }

    @Test
    void getState() throws SQLException {
        OrderProcessingPointDto orderProcessingPointToTest = new OrderProcessingPointDto();
        orderProcessingPointToTest.setId(1L);
        orderProcessingPointToTest.setLocation("Russia");
        orderProcessingPointToTest.setWarehouse(new WarehouseDto());

        OrderDto orderToTest = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(orderProcessingPointToTest)
                .currentLocation(orderProcessingPointToTest)
                .price(BigDecimal.valueOf(1))
                .recipient(ClientDto.builder().build())
                .sender(ClientDto.builder().build())
                .state(OrderStateDto.builder().build())
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
                .build();

        orderService.save(orderToTest);

        String actual = orderService.getState(1);

        String expected = "READY_TO_SEND";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void compareOrders() throws SQLException {
        OrderProcessingPointDto firstProcessingPointToTest = new OrderProcessingPointDto();
        firstProcessingPointToTest.setId(1L);
        firstProcessingPointToTest.setLocation("Russia");
        firstProcessingPointToTest.setWarehouse(new WarehouseDto());

        OrderProcessingPointDto secondProcessingPointToTest = new OrderProcessingPointDto();
        secondProcessingPointToTest.setId(2L);
        secondProcessingPointToTest.setLocation("Russia");
        secondProcessingPointToTest.setWarehouse(new WarehouseDto());

        OrderDto firstOrderToTest = OrderDto.builder()
                .id(1L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .recipient(ClientDto.builder().build())
                .sender(ClientDto.builder().build())
                .destinationPlace(firstProcessingPointToTest)
                .price(BigDecimal.valueOf(1))
                .currentLocation(firstProcessingPointToTest)
                .state(OrderStateDto.builder().build())
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
                .recipient(ClientDto.builder().build())
                .build();

        OrderDto secondOrderToTest = OrderDto.builder()
                .id(2L)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1.0)
                        .width(1.0)
                        .length(1.0)
                        .weight(20.0).build())
                .destinationPlace(secondProcessingPointToTest)
                .currentLocation(secondProcessingPointToTest)
                .sender(ClientDto.builder().build())
                .recipient(ClientDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .state(OrderStateDto.builder().build())
                .history(Collections.singletonList(OrderHistoryDto.builder().user(UserDto.builder().build()).build()))
                .recipient(ClientDto.builder().build())
                .build();

        orderService.save(firstOrderToTest);
        orderService.save(secondOrderToTest);

        List<OrderDto> actual = Arrays.asList(
                firstOrderToTest, secondOrderToTest
        );

        orderService.send(actual);

        List<List<Order>> ordersOnTheWay = orderService.getOrdersOnTheWay();

        actual.get(0).setId(5L);

        List<Order> expected = ordersOnTheWay.get(0);

        final List<OrderDto> actualOrderDtos = expected.stream()
                .map(CustomModelMapper::mapDtoToOrder)
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
