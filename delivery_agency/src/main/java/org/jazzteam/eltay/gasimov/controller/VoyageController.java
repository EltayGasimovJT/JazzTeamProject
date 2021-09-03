package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.VoyageDto;
import org.jazzteam.eltay.gasimov.service.VoyageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
public class VoyageController {
    @Autowired
    private VoyageService voyageService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/voyages")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    VoyageDto addNewVoyage(@RequestBody VoyageDto voyageDto) {
        return modelMapper.map(voyageService.save(voyageDto), VoyageDto.class);
    }

    @GetMapping(path = "/voyages")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<VoyageDto> findAllVoyages() {
        return voyageService.findAll().stream()
                .map(orderProcessingPoint -> modelMapper.map(orderProcessingPoint, VoyageDto.class))
                .collect(Collectors.toSet());
    }

    @DeleteMapping(path = "/voyages/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVoyage(@PathVariable Long id) {
        voyageService.delete(id);
    }

    @GetMapping(path = "/voyages/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    VoyageDto findById(@PathVariable Long id) {
        return modelMapper.map(voyageService.findOne(id), VoyageDto.class);
    }

    @PutMapping("/voyages")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public VoyageDto updateVoyage(@RequestBody VoyageDto newVoyage) {
        return modelMapper.map(voyageService.save(newVoyage), VoyageDto.class);
    }
}
