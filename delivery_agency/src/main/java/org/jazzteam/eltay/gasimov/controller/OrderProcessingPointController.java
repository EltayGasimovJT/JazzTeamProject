package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;
import org.jazzteam.eltay.gasimov.service.OrderProcessingPointService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.jazzteam.eltay.gasimov.controller.constants.ControllerConstant.PROCESSING_POINTS_BY_ID_URL;
import static org.jazzteam.eltay.gasimov.controller.constants.ControllerConstant.PROCESSING_POINTS_URL;

@RestController
public class OrderProcessingPointController {
    @Autowired
    private OrderProcessingPointService processingPointService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = PROCESSING_POINTS_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    OrderProcessingPointDto addNewProcessingPoint(@RequestBody OrderProcessingPointDto processingPointDto) {
        return modelMapper.map(processingPointService.save(processingPointDto), OrderProcessingPointDto.class);
    }

    @GetMapping(path = PROCESSING_POINTS_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<OrderProcessingPointDto> findAllProcessingPoints() {
        return processingPointService.findAll()
                .stream()
                .map(orderProcessingPoint -> modelMapper.map(orderProcessingPoint, OrderProcessingPointDto.class))
                .collect(Collectors.toList());
    }

    @DeleteMapping(path = PROCESSING_POINTS_BY_ID_URL)
    public void deleteProcessingPoint(@PathVariable Long id) {
        processingPointService.delete(id);
    }

    @GetMapping(path = PROCESSING_POINTS_BY_ID_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    OrderProcessingPointDto findById(@PathVariable Long id) {
        return modelMapper.map(processingPointService.findOne(id), OrderProcessingPointDto.class);
    }

    @PutMapping(PROCESSING_POINTS_URL)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public OrderProcessingPointDto updateProcessingPoint(@RequestBody OrderProcessingPointDto newProcessingPoint) {
        return modelMapper.map(processingPointService.update(newProcessingPoint), OrderProcessingPointDto.class);
    }
}
