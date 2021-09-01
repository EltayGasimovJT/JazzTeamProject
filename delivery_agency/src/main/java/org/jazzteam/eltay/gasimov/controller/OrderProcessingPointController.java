package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;
import org.jazzteam.eltay.gasimov.service.OrderProcessingPointService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public @ResponseBody
    OrderProcessingPointDto addNewProcessingPoint(@RequestBody OrderProcessingPointDto processingPointDto) {
        return modelMapper.map(processingPointService.save(processingPointDto), OrderProcessingPointDto.class);
    }

    @GetMapping(path = "/processingPoints")
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
    public @ResponseBody
    OrderProcessingPointDto findById(@PathVariable Long id) {
        return modelMapper.map(processingPointService.findOne(id), OrderProcessingPointDto.class);
    }

    @PutMapping("/processingPoints")
    public OrderProcessingPointDto updateProcessingPoint(@RequestBody OrderProcessingPointDto newProcessingPoint) {
        if (processingPointService.findOne(newProcessingPoint.getId()) == null) {
            return modelMapper.map(processingPointService.save(newProcessingPoint), OrderProcessingPointDto.class);
        } else {
            return modelMapper.map(processingPointService.update(newProcessingPoint), OrderProcessingPointDto.class);
        }
    }
}
