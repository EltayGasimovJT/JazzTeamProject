package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.service.OrderProcessingPointService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.stream.Collectors;

@RestController
public class OrderProcessingPointController {
    @Autowired
    private OrderProcessingPointService processingPointService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/processingPoints")
    public @ResponseBody
    OrderProcessingPointDto addNewProcessingPoint(@RequestBody OrderProcessingPointDto processingPointDto) throws SQLException {
        return modelMapper.map(processingPointService.save(processingPointDto), OrderProcessingPointDto.class);
    }

    @GetMapping(path = "/processingPoints")
    public @ResponseBody
    Iterable<OrderProcessingPointDto> findAllProcessingPoints() throws SQLException {
        return processingPointService.findAll().stream()
                .map(orderProcessingPoint -> modelMapper.map(orderProcessingPoint, OrderProcessingPointDto.class))
                .collect(Collectors.toSet());
    }

    @DeleteMapping(path = "/processingPoints/{id}")
    public void deleteProcessingPoint(@PathVariable Long id) throws SQLException {
        processingPointService.delete(id);
    }

    @GetMapping(path = "/processingPoints/{id}")
    public @ResponseBody
    OrderProcessingPointDto findById(@PathVariable Long id) throws SQLException {
        return modelMapper.map(processingPointService.findOne(id), OrderProcessingPointDto.class);
    }

    @PutMapping("/processingPoints")
    public OrderProcessingPointDto updateProcessingPoint(@RequestBody OrderProcessingPointDto newProcessingPoint) throws SQLException {
        if (processingPointService.findOne(newProcessingPoint.getId()) == null) {
            return modelMapper.map(processingPointService.save(newProcessingPoint), OrderProcessingPointDto.class);
        } else {
            return modelMapper.map(processingPointService.update(newProcessingPoint), OrderProcessingPointDto.class);
        }
    }
}
