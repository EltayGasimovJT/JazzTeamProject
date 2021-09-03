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

@RestController
public class OrderProcessingPointController {
    @Autowired
    private OrderProcessingPointService processingPointService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/processingPoints")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    OrderProcessingPointDto addNewProcessingPoint(@RequestBody OrderProcessingPointDto processingPointDto) {
        return modelMapper.map(processingPointService.save(processingPointDto), OrderProcessingPointDto.class);
    }

    @GetMapping(path = "/processingPoints")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<Long> findAllProcessingPoints() {
        List<Long> listOfWarehousesId = new ArrayList<>();
        for (OrderProcessingPoint processingPoint : processingPointService.findAll()) {
            listOfWarehousesId.add(processingPoint.getId());
        }
        return listOfWarehousesId;
    }

    @DeleteMapping(path = "/processingPoints/{id}")
    public void deleteProcessingPoint(@PathVariable Long id) {
        processingPointService.delete(id);
    }

    @GetMapping(path = "/processingPoints/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    OrderProcessingPointDto findById(@PathVariable Long id) {
        return modelMapper.map(processingPointService.findOne(id), OrderProcessingPointDto.class);
    }

    @PutMapping("/processingPoints")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public OrderProcessingPointDto updateProcessingPoint(@RequestBody OrderProcessingPointDto newProcessingPoint) {
        return modelMapper.map(processingPointService.update(newProcessingPoint), OrderProcessingPointDto.class);
    }
}
