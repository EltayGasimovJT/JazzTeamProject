package org.jazzteam.eltay.gasimov.service;

import javassist.tools.rmi.ObjectNotFoundException;
import org.jazzteam.eltay.gasimov.dto.*;
import org.jazzteam.eltay.gasimov.entity.*;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class TicketServiceTest {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private OrderProcessingPointService orderProcessingPointService;
    @Autowired
    private OrderStateService orderStateService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private ClientService clientService;

    @Test
    void findById() {
        TicketDto expectedDto = TicketDto.builder()
                .ticketNumber("12qwrq21")
                .build();
        TicketDto expected = modelMapper.map(ticketService.save(expectedDto), TicketDto.class);
        TicketDto actual = modelMapper.map(ticketService.findById(expected.getId()), TicketDto.class);
        assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        TicketDto firstTicket = TicketDto.builder()
                .ticketNumber("12qwrq21")
                .build();
        TicketDto secondTicket = TicketDto.builder()
                .ticketNumber("1wq2qwrq21")
                .build();
        Ticket savedFirst = ticketService.save(firstTicket);
        Ticket savedSecond = ticketService.save(secondTicket);
        List<Ticket> actual = ticketService.findAll();
        assertEquals(Arrays.asList(savedFirst, savedSecond), actual);
    }

    @Test
    void delete() {
        TicketDto firstTicket = TicketDto.builder()
                .ticketNumber("12qwrq21")
                .build();
        TicketDto secondTicket = TicketDto.builder()
                .ticketNumber("1wq2qwrq21")
                .build();
        Ticket savedFirst = ticketService.save(firstTicket);
        Ticket savedSecond = ticketService.save(secondTicket);
        ticketService.delete(savedFirst.getId());
        List<Ticket> actual = ticketService.findAll();
        assertEquals(Collections.singletonList(savedSecond), actual);
    }

    @Test
    void save() {
        TicketDto expected = TicketDto.builder()
                .ticketNumber("12qwrq21")
                .build();
        TicketDto actual = modelMapper.map(ticketService.save(expected), TicketDto.class);
        expected.setId(actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    void findByTicketNumber() {
        TicketDto expectedDto = TicketDto.builder()
                .ticketNumber("12qwrq21")
                .build();
        TicketDto expected = modelMapper.map(ticketService.save(expectedDto), TicketDto.class);
        TicketDto actual = modelMapper.map(ticketService.findByTicketNumber(expected.getTicketNumber()), TicketDto.class);
        assertEquals(expected, actual);
    }

    @Test
    void generateTicket() throws ObjectNotFoundException {
        CreateOrderRequestDto expectedOrder = getOrder();

        Order expected = orderService.createOrder(expectedOrder);

        Ticket ticket = ticketService.generateTicket(expected.getId());
        Assertions.assertNotNull(ticket);
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

        AbstractBuildingDto currentLocationToTest = new OrderProcessingPointDto();
        currentLocationToTest.setLocation("Полоцк-Беларусь");

        OrderProcessingPointDto destinationPlaceToTest = new OrderProcessingPointDto();
        destinationPlaceToTest.setLocation("Минск-Беларусь");
        destinationPlaceToTest.setWorkingPlaceType(WorkingPlaceType.PROCESSING_POINT);
        destinationPlaceToTest.setWarehouse(CustomModelMapper.mapWarehouseToDto(warehouseService.findByLocation(location)));

        orderProcessingPointService.save(destinationPlaceToTest);

        WorkerDto workerToSave = WorkerDto.builder()
                .name("Вася")
                .surname("Васильев")
                .role(Role.ROLE_ADMIN)
                .password("rqweqwqwe")
                .workingPlace(modelMapper.map(orderProcessingPointService.findByLocation("Минск-Беларусь"), OrderProcessingPointDto.class))
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