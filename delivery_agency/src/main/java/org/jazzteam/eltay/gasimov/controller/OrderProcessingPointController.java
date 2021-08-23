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
    OrderProcessingPointDto addNewCoefficient(@RequestBody OrderProcessingPointDto processingPointDto) throws SQLException {
        return modelMapper.map(processingPointService.save(processingPointDto), OrderProcessingPointDto.class);
    }

    @GetMapping(path = "/processingPoints")
    public @ResponseBody
    Iterable<OrderProcessingPointDto> findAllCoefficients() throws SQLException {
        return processingPointService.findAll().stream()
                .map(orderProcessingPoint -> modelMapper.map(orderProcessingPoint, OrderProcessingPointDto.class))
                .collect(Collectors.toSet());
    }

    @DeleteMapping(path = "/processingPoints/{id}")
    public void deleteCoefficient(@PathVariable Long id) throws SQLException {
        processingPointService.delete(id);
    }

    @PutMapping("/processingPoints")
    public OrderProcessingPointDto updateCoefficient(@RequestBody OrderProcessingPointDto newProcessingPoint) throws SQLException {
        if (processingPointService.findOne(newProcessingPoint.getId()) == null) {
            return modelMapper.map(processingPointService.save(newProcessingPoint), OrderProcessingPointDto.class);
        } else {
            return modelMapper.map(processingPointService.update(newProcessingPoint), OrderProcessingPointDto.class);
        }
    }
}
