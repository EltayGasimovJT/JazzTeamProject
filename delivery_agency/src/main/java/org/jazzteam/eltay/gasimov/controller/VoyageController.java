package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.VoyageDto;
import org.jazzteam.eltay.gasimov.service.VoyageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.jazzteam.eltay.gasimov.util.Constants.VOYAGES_BY_ID_URL;
import static org.jazzteam.eltay.gasimov.util.Constants.VOYAGES_URL;

@RestController
public class VoyageController {
    @Autowired
    private VoyageService voyageService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = VOYAGES_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    VoyageDto addNewVoyage(@RequestBody VoyageDto voyageDto) {
        return modelMapper.map(voyageService.save(voyageDto), VoyageDto.class);
    }

    @GetMapping(path = VOYAGES_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<VoyageDto> findAllVoyages() {
        return voyageService.findAll().stream()
                .map(orderProcessingPoint -> modelMapper.map(orderProcessingPoint, VoyageDto.class))
                .collect(Collectors.toSet());
    }

    @DeleteMapping(path = VOYAGES_BY_ID_URL)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVoyage(@PathVariable Long id) {
        voyageService.delete(id);
    }

    @GetMapping(path = VOYAGES_BY_ID_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    VoyageDto findById(@PathVariable Long id) {
        return modelMapper.map(voyageService.findOne(id), VoyageDto.class);
    }

    @PutMapping(path = VOYAGES_URL)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public VoyageDto updateVoyage(@RequestBody VoyageDto newVoyage) {
        return modelMapper.map(voyageService.save(newVoyage), VoyageDto.class);
    }
}
