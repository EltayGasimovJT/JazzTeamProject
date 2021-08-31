package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.OrderStateDto;
import org.jazzteam.eltay.gasimov.service.OrderStateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
public class OrderStateController {
    @Autowired
    private OrderStateService orderStateService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/orderStates")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    OrderStateDto addNewState(@RequestBody OrderStateDto orderStateToSave) {
        return modelMapper.map(orderStateService.save(orderStateToSave), OrderStateDto.class);
    }

    @GetMapping(path = "/orderStates/{id}")
    public @ResponseBody
    OrderStateDto findById(@PathVariable Long id) {
        return modelMapper.map(orderStateService.findOne(id), OrderStateDto.class);
    }

    @GetMapping(path = "/orderStates")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<OrderStateDto> findAllStates() {
        return orderStateService.findAll()
                .stream()
                .map(orderState -> modelMapper.map(orderState, OrderStateDto.class))
                .collect(Collectors.toList());
    }

    @DeleteMapping(path = "/orderStates/{id}")
    public void deleteState(@PathVariable Long id) {
        orderStateService.delete(id);
    }

    @PutMapping("/orderStates")
    @ResponseStatus(HttpStatus.OK)
    public OrderStateDto updateState(@RequestBody OrderStateDto orderStateDto) {
        if (orderStateService.findOne(orderStateDto.getId()) == null) {
            return modelMapper.map(orderStateService.save(orderStateDto), OrderStateDto.class);
        } else {
            OrderStateDto orderStateDtoToUpdate = OrderStateDto.builder()
                    .id(orderStateDto.getId())
                    .state(orderStateDto.getState())
                    .build();
            return modelMapper.map(orderStateService.update(orderStateDtoToUpdate), OrderStateDto.class);
        }
    }
}
