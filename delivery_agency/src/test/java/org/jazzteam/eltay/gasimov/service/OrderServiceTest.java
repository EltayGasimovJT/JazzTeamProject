package org.jazzteam.eltay.gasimov.service;

import javassist.tools.rmi.ObjectNotFoundException;
import org.jazzteam.eltay.gasimov.dto.*;
import org.jazzteam.eltay.gasimov.entity.*;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
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
    @Autowired
    private CoefficientForPriceCalculationService coefficientForPriceCalculationService;
    @Autowired
    private ClientService clientService;

    private static Stream<Arguments> testDataForCalculate() {
        OrderProcessingPointDto processingPointToTest = new OrderProcessingPointDto();
        processingPointToTest.setLocation("Russia");
        OrderDto firstOrderToTest = OrderDto.builder()
                .parcelParameters(ParcelParametersDto.builder()
                        .height(1231.0)
                        .width(132.0)
                        .length(142.0)
                        .weight(2220.0).build()
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
                        .height(421.0)
                        .width(102.0)
                        .length(142.0)
                        .weight(2110.0).build()
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
                Arguments.of(firstOrderToTest, BigDecimal.valueOf(7.462)),
                Arguments.of(secondOrderToTest, BigDecimal.valueOf(7.405))
        );
    }

    @Test
    void create() throws ObjectNotFoundException {
        CreateOrderRequestDto expectedOrder = getOrder();

         Order expected = orderService.createOrder(expectedOrder);

        Order actual = orderService.findOne(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    @Disabled
    void deleteByTrackNumber() throws ObjectNotFoundException {
        CreateOrderRequestDto expectedOrder = getOrder();

        Order expected = orderService.createOrder(expectedOrder);

        orderService.deleteByTrackNumber(expected.getTrackNumber());

        Assertions.assertTrue(orderService.findAll().isEmpty());
    }

    @Test
    void findById() throws ObjectNotFoundException {
        CreateOrderRequestDto expectedOrderDto = getOrder();

        Order expectedOrder = orderService.createOrder(expectedOrderDto);

        Order actual = orderService.findOne(expectedOrder.getId());

        assertEquals(expectedOrder, actual);
    }

    @Test
    @Disabled
    void findByRecipient() throws ObjectNotFoundException {
        CreateOrderRequestDto expectedOrderDto = getOrder();

        Order expectedOrder = orderService.createOrder(expectedOrderDto);

        Order actual = orderService.findOne(expectedOrder.getId());

        Order actualOrder = orderService.findByRecipient(modelMapper.map(actual.getRecipient(), ClientDto.class));

        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    @Disabled
    void findBySender() throws ObjectNotFoundException {
        CreateOrderRequestDto expectedOrderDto = getOrder();

        Order expectedOrder = orderService.createOrder(expectedOrderDto);

        Order actual = orderService.findOne(expectedOrder.getId());

        Order actualOrder = orderService.findBySender(modelMapper.map(actual.getRecipient(), ClientDto.class));

        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void getState() throws ObjectNotFoundException {
        CreateOrderRequestDto expectedOrderDto = getOrder();

        Order expectedOrder = orderService.createOrder(expectedOrderDto);

        Order actualOrder = orderService.findOne(expectedOrder.getId());

        String expected = "Готов к отправке";

        final String actual = orderService.getState(actualOrder.getId());
        assertEquals(expected, actual);
    }

    @Test
    @Disabled
    void findAll() throws ObjectNotFoundException {
        CreateOrderRequestDto expectedOrderDto = getOrder();
        orderService.createOrder(expectedOrderDto);

        Assertions.assertFalse(orderService.findAll().isEmpty());
    }


    @ParameterizedTest
    @MethodSource("testDataForCalculate")
    void calculatePrice(OrderDto order, BigDecimal expectedPrice) throws ObjectNotFoundException {
        CoefficientForPriceCalculationDto coefficientToTest = CoefficientForPriceCalculationDto
                .builder()
                .countryCoefficient(1.5)
                .country("Poland")
                .parcelSizeLimit(60)
                .build();
        coefficientForPriceCalculationService.save(coefficientToTest);
        BigDecimal actualPrice = orderService.calculatePrice(order);

        assertEquals(expectedPrice.doubleValue(), actualPrice.doubleValue(), 0.001);
    }

    private CreateOrderRequestDto getOrder() throws ObjectNotFoundException {
        WarehouseDto warehouseToSave = new WarehouseDto();
        final String location = "Беларусь";
        warehouseToSave.setLocation(location);
        warehouseToSave.setWorkingPlaceType(WorkingPlaceType.WAREHOUSE);
        warehouseService.save(warehouseToSave);
        OrderStateDto stateDtoToSave = OrderStateDto.builder()
                .state(OrderStates.READY_TO_SEND.getState())
                .prefix(" weq")
                .suffix(" weq")
                .build();
        orderStateService.save(stateDtoToSave);
        OrderProcessingPointDto destinationPlaceToTest = new OrderProcessingPointDto();
        destinationPlaceToTest.setLocation("Минск-Беларусь");
        destinationPlaceToTest.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);
        destinationPlaceToTest.setWarehouse(CustomModelMapper.mapWarehouseToDto(warehouseService.findByLocation(location)));

        OrderProcessingPoint savedProcessingPoint = orderProcessingPointService.save(destinationPlaceToTest);

        WorkerDto workerToSave = WorkerDto.builder()
                .name("Вася")
                .surname("Васильев")
                .role(Role.ROLE_ADMIN)
                .password("rqweqwqwe")
                .workingPlace(modelMapper.map(savedProcessingPoint, OrderProcessingPointDto.class))
                .build();

        workerService.save(workerToSave);

        ClientDto recipient = ClientDto.builder()
                .name("Олег")
                .surname("Голубев")
                .phoneNumber("124125")
                .passportId("124241")
                .build();
        ClientDto sender = ClientDto.builder()
                .name("Эльтай")
                .surname("Гасымов")
                .phoneNumber("44234242")
                .passportId("23535121")
                .build();
        Client savedSender = clientService.save(sender);
        Client savedRecipient = clientService.save(recipient);
        return CreateOrderRequestDto.builder()
                .destinationPoint("Минск-Беларусь")
                .parcelParameters(ParcelParametersDto.builder()
                                .length(50.0)
                                .weight(50.0)
                                .width(50.0)
                                .height(50.0)
                                .build())
                .price(BigDecimal.valueOf(30.0))
                .recipient(modelMapper.map(savedRecipient, ClientDto.class))
                .sender(modelMapper.map(savedSender, ClientDto.class))
                .workerDto(workerToSave)
                .build();
    }
}
