package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.OrderDto;
import org.jazzteam.eltay.gasimov.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.stream.Collectors;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/clients")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    OrderDto addNewUser(@RequestBody OrderDto orderDtoToSave) throws SQLException {

        return modelMapper.map(orderService.save(orderDtoToSave), OrderDto.class);
    }

    @GetMapping(path = "/clients/{id}")
    public @ResponseBody
    OrderDto findById(@PathVariable Long id) throws SQLException {
        return modelMapper.map(orderService.findOne(id), OrderDto.class);
    }

    @GetMapping(path = "/clients")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<OrderDto> findAllUsers() throws SQLException {
        return orderService.findAll()
                .stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toSet());
    }

    @DeleteMapping(path = "/clients/{id}")
    public void deleteCoefficient(@PathVariable Long id) {
        orderService.delete(id);
    }

    @PutMapping("/clients")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto replaceEmployee(@RequestBody OrderDto newOrder) throws SQLException {
        if (orderService.findOne(newOrder.getId()) == null) {
            return modelMapper.map(orderService.save(newOrder), OrderDto.class);
        } else {
            return modelMapper.map(orderService.update(newOrder), OrderDto.class);
        }
    }
}
