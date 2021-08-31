package org.jazzteam.eltay.gasimov.controller;

import lombok.extern.java.Log;
import org.jazzteam.eltay.gasimov.dto.ClientDto;
import org.jazzteam.eltay.gasimov.dto.CreateOrderRequestDto;
import org.jazzteam.eltay.gasimov.dto.OrderDto;
import org.jazzteam.eltay.gasimov.dto.ParcelParametersDto;
import org.jazzteam.eltay.gasimov.service.ClientService;
import org.jazzteam.eltay.gasimov.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Log
public class OrderController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    OrderDto addNewOrder(@RequestBody OrderDto orderDtoToSave) {
        return modelMapper.map(orderService.save(orderDtoToSave), OrderDto.class);
    }

    @PostMapping(path = "/createOrder")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    OrderDto createOrder(@RequestBody CreateOrderRequestDto dtoFromForm) {
        ClientDto sender = ClientDto.builder()
                .name(dtoFromForm.getSenderName())
                .surname(dtoFromForm.getSenderSurname())
                .phoneNumber(dtoFromForm.getSenderPhoneNumber())
                .passportId(dtoFromForm.getSenderPassportId())
                .build();
        ClientDto recipient = ClientDto.builder()
                .name(dtoFromForm.getRecipientName())
                .surname(dtoFromForm.getRecipientSurname())
                .phoneNumber(dtoFromForm.getRecipientPhoneNumber())
                .passportId(dtoFromForm.getRecipientPassportId())
                .build();

        OrderDto orderDtoToSave = OrderDto.builder()
                .recipient(recipient)
                .parcelParameters(ParcelParametersDto.builder()
                        .height(dtoFromForm.getHeight())
                        .weight(dtoFromForm.getWeight())
                        .width(dtoFromForm.getWidth())
                        .length(dtoFromForm.getLength())
                        .build())
                 //.destinationPlace(modelMapper.map(orderProcessingPointService.findByLocation(dtoFromForm.getDestinationPlaceCountry()), OrderProcessingPointDto.class))
                .build();
        Set<OrderDto> ordersToSave = new HashSet<>();
        sender.setOrders(ordersToSave);
        clientService.save(sender);
        clientService.save(recipient);

        orderService.save(orderDtoToSave);
        return orderDtoToSave;
    }

    @GetMapping(path = "/orders/findBySenderPassport")
    public @ResponseBody
    Iterable<OrderDto> findByClientsPassportId(@RequestParam String passportId, Map<String,Object> model) {
        final ClientDto map = modelMapper.map(clientService.findByPassportId(passportId), ClientDto.class);
        model.put("Orders", map.getOrders());
        return map.getOrders();
    }

    @GetMapping(path = "/orders/{id}")
    public @ResponseBody
    OrderDto findById(@PathVariable Long id)  {
        return modelMapper.map(orderService.findOne(id), OrderDto.class);
    }

    @GetMapping(path = "/orders")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<OrderDto> findAllOrders() {
        final Set<OrderDto> collect = orderService.findAll()
                .stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toSet());
        return collect;
    }

    @DeleteMapping(path = "/orders/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
    }

    @PutMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto updateOrder(@RequestBody OrderDto newOrder) {
        if (orderService.findOne(newOrder.getId()) == null) {
            return modelMapper.map(orderService.save(newOrder), OrderDto.class);
        } else {
            return modelMapper.map(orderService.update(newOrder), OrderDto.class);
        }
    }
}
