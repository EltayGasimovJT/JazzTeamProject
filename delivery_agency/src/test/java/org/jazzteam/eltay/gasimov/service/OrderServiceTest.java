package org.jazzteam.eltay.gasimov.service;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.SneakyThrows;
import org.jazzteam.eltay.gasimov.dto.*;
import org.jazzteam.eltay.gasimov.entity.*;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private OrderProcessingPointService orderProcessingPointService;
    @Autowired
    private OrderStateService orderStateService;
    @Autowired
    private WorkerService workerService;

    private static Stream<Arguments> testDataForCalculate() {
        OrderProcessingPointDto processingPointToTest = new OrderProcessingPointDto();
        processingPointToTest.setLocation("Russia");
        OrderDto firstOrderToTest = OrderDto.builder()
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
                .history(Stream.of(OrderHistoryDto.builder().worker(WorkerDto.builder().build()).build()).collect(Collectors.toSet()))
                .build();

        processingPointToTest.setLocation("Poland");
        processingPointToTest.setId(2L);
        OrderDto secondOrderToTest = OrderDto.builder()
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
                .history(Stream.of(OrderHistoryDto.builder().worker(WorkerDto.builder().build()).build()).collect(Collectors.toSet()))
                .build();
        return Stream.of(
                Arguments.of(firstOrderToTest, BigDecimal.valueOf(72.0)),
                Arguments.of(secondOrderToTest, BigDecimal.valueOf(108.0))
        );
    }

    @Test
    void updateOrderCurrentLocation() throws ObjectNotFoundException {
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
                .history(Stream.of(OrderHistoryDto.builder().worker(WorkerDto.builder().build()).build()).collect(Collectors.toSet()))
                .currentLocation(orderProcessingPointToTest)
                .build();


        orderService.save(expectedDto);

        orderProcessingPointToTest.setLocation("Poland");

        orderService.updateOrderCurrentLocation(expectedDto.getId(), orderProcessingPointToTest);

        Order actual = orderService.findOne(1);

        OrderDto actualDto = CustomModelMapper.mapOrderToDto(actual);

        Assertions.assertEquals(expectedDto, actualDto);
    }

    @Test
    void create() throws ObjectNotFoundException {
        WarehouseDto warehouseToSave = new WarehouseDto();
        warehouseToSave.setLocation("Беларусь");
        warehouseToSave.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);
        warehouseToSave.setExpectedOrders(new ArrayList<>());
        warehouseToSave.setDispatchedOrders(new ArrayList<>());
        OrderStateDto stateDtoToSave = OrderStateDto.builder()
                .state(OrderStates.READY_TO_SEND.getState())
                .prefix(" weq")
                .suffix(" weq")
                .build();
        orderStateService.save(stateDtoToSave);

        AbstractBuildingDto currentLocationToTest = new OrderProcessingPointDto();
        currentLocationToTest.setLocation("Полоцк-Беларусь");

        OrderProcessingPointDto destinationPlaceToTest = new OrderProcessingPointDto();
        destinationPlaceToTest.setLocation("Минск-Беларусь");
        destinationPlaceToTest.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);
        warehouseToSave.setOrderProcessingPoints(Collections.singletonList(destinationPlaceToTest));
        warehouseService.save(warehouseToSave);

        WorkerDto workerToSave = WorkerDto.builder()
                .name("Вася")
                .surname("Васильев")
                .role(Role.ROLE_ADMIN)
                .password("rqweqwqwe")
                .workingPlace(destinationPlaceToTest)
                .build();

        CreateOrderRequestDto expectedOrder = CreateOrderRequestDto.builder()
                .destinationPoint("Минск-Беларусь")
                .parcelParameters(
                        ParcelParametersDto.builder()
                                .length(50.0)
                                .weight(50.0)
                                .width(50.0)
                                .height(50.0)
                                .build()
                )
                .price(BigDecimal.valueOf(30.0))
                .recipient(
                        ClientDto.builder()
                                .name("Олег")
                                .surname("Голубев")
                                .phoneNumber("124125")
                                .passportId("124241")
                                .build()
                )
                .sender(
                        ClientDto.builder()
                                .name("Эльтай")
                                .surname("Гасымов")
                                .phoneNumber("44234242")
                                .passportId("23535121")
                                .build()
                )
                .workerDto(workerToSave)
                .build();


        Order expected = orderService.createOrder(expectedOrder);

        Order actual = orderService.findOne(expected.getId());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findById() throws ObjectNotFoundException {
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
                .history(Stream.of(OrderHistoryDto.builder().worker(WorkerDto.builder().build()).build()).collect(Collectors.toSet()))
                .build();

        orderService.save(expectedOrder);

        Order actualOrder = orderService.findOne(1);

        OrderDto actualOrderDto = CustomModelMapper.mapOrderToDto(actualOrder);

        Assertions.assertEquals(expectedOrder, actualOrderDto);
    }

    @Test
    void findByRecipient() throws ObjectNotFoundException {
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
                .history(Stream.of(OrderHistoryDto.builder().worker(WorkerDto.builder().build()).build()).collect(Collectors.toSet()))
                .build();

        orderService.save(expectedOrder);

        Order actualOrder = orderService.findByRecipient(recipientToTest);

        OrderDto actualOrderDto = CustomModelMapper.mapOrderToDto(actualOrder);

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
                .history(Stream.of(OrderHistoryDto.builder().worker(WorkerDto.builder().build()).build()).collect(Collectors.toSet()))
                .build();

        orderService.save(expectedOrder);

        Order actualOrder = orderService.findByRecipient(senderToTest);

        OrderDto actualOrderDto = CustomModelMapper.mapOrderToDto(actualOrder);

        Assertions.assertEquals(expectedOrder, actualOrderDto);

    }

    @Test
    void getState() throws ObjectNotFoundException {
        WarehouseDto warehouseToSave = new WarehouseDto();
        warehouseToSave.setLocation("Belarus");
        warehouseToSave.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);
        warehouseToSave.setExpectedOrders(new ArrayList<>());
        warehouseToSave.setDispatchedOrders(new ArrayList<>());
        Warehouse savedWarehouse = warehouseService.save(warehouseToSave);
        OrderProcessingPointDto orderProcessingPointToTest = new OrderProcessingPointDto();
        orderProcessingPointToTest.setLocation("Russia");
        orderProcessingPointToTest.setWarehouse(CustomModelMapper.mapWarehouseToDto(savedWarehouse));

        OrderDto orderToTest = OrderDto.builder()
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
                .history(Stream.of(OrderHistoryDto.builder().worker(WorkerDto.builder().build()).build()).collect(Collectors.toSet()))
                .build();

        Order savedOrder = orderService.save(orderToTest);

        String actual = orderService.getState(savedOrder.getId());

        String expected = "READY_TO_SEND";

        Assertions.assertEquals(expected, actual);
    }


    @ParameterizedTest
    @MethodSource("testDataForCalculate")
    void calculatePrice(OrderDto order, BigDecimal expectedPrice) throws ObjectNotFoundException {
        BigDecimal actualPrice = orderService.calculatePrice(order);

        Assertions.assertEquals(expectedPrice.doubleValue(), actualPrice.doubleValue(), 0.001);
    }
}
