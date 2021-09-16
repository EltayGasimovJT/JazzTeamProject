package org.jazzteam.eltay.gasimov.controller;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.extern.java.Log;
import org.jazzteam.eltay.gasimov.controller.security.CustomUserDetails;
import org.jazzteam.eltay.gasimov.dto.*;
import org.jazzteam.eltay.gasimov.entity.Order;
import org.jazzteam.eltay.gasimov.entity.Worker;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.jazzteam.eltay.gasimov.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

import static org.jazzteam.eltay.gasimov.util.Constants.*;

@RestController
@Log
public class OrderController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private ContextService contextService;

    @PostMapping(path = ORDERS_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    OrderDto addNewOrder(@RequestBody OrderDto orderDtoToSave) throws ObjectNotFoundException {
        return modelMapper.map(orderService.save(orderDtoToSave), OrderDto.class);
    }

    @PostMapping(path = ORDERS_CREATE_ORDER_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    OrderResponseDto createOrder(@RequestBody CreateOrderRequestDto requestOrder) throws ObjectNotFoundException {
        CustomUserDetails principal = contextService.getCurrentUserFromContext();
        Worker foundWorker = workerService.findByName(principal.getUsername());
        requestOrder.setWorkerDto(CustomModelMapper.mapUserToDto(foundWorker));
        OrderDto createdOrder = CustomModelMapper.mapOrderToDto(orderService.createOrder(requestOrder));
        return OrderResponseDto.builder()
                .orderDto(createdOrder)
                .ticketDto(modelMapper.map(ticketService.generateTicket(createdOrder.getId()), TicketDto.class))
                .build();
    }

    @GetMapping(path = ORDERS_FIND_BY_SENDER_PASSPORT_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<OrderDto> findByClientsPassportId(@RequestParam String passportId) throws ObjectNotFoundException {
        Set<Order> ordersBySenderPassportId = clientService.findClientByPassportId(passportId).getOrders();
        return ordersBySenderPassportId.stream()
                .map(CustomModelMapper::mapOrderToDto)
                .collect(Collectors.toSet());
    }

    @GetMapping(path = ORDERS_FIND_HISTORY_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<OrderHistoryDto> findOrderHistoryById(@PathVariable Long id) {
        Order foundOrder = orderService.findOne(id);
        return foundOrder.getHistory().stream()
                .map(orderHistory -> modelMapper.map(orderHistory, OrderHistoryDto.class))
                .collect(Collectors.toSet());
    }

    @GetMapping(path = ORDERS_FIND_BY_TRACK_NUMBER_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    OrderDto findByOrderTrackNumber(@RequestParam String orderNumber) {
        return modelMapper.map(orderService.findByTrackNumber(orderNumber), OrderDto.class);
    }

    @GetMapping(path = ORDERS_BY_ID_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    OrderDto findById(@PathVariable Long id) {
        return modelMapper.map(orderService.findOne(id), OrderDto.class);
    }

    @GetMapping(path = ORDERS_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<OrderDto> findAllOrders() {
        return orderService.findAll()
                .stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toSet());
    }

    @DeleteMapping(path = ORDERS_BY_ID_URL)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        log.severe(id.toString());
        orderService.delete(id);
    }

    @DeleteMapping(path = ORDERS_BY_TRACK_NUMBER)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByTrackNumber(@PathVariable String orderNumber) {
        orderService.deleteByTrackNumber(orderNumber);
    }

    @PutMapping(path = ORDERS_CHANGE_ORDER_STATE_URL)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public OrderDto changeOrderState(@RequestBody OrderChangeStateDto newState) {
        return modelMapper.map(orderService.changeOrderState(newState.getOrderNumber(), newState.getOrderState()), OrderDto.class);
    }

    @PutMapping(path = ORDERS_URL)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public OrderDto updateOrder(@RequestBody OrderDto newOrder) {
        return modelMapper.map(orderService.update(newOrder), OrderDto.class);
    }
}
