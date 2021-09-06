package org.jazzteam.eltay.gasimov.controller;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.extern.java.Log;
import org.jazzteam.eltay.gasimov.dto.CoefficientForPriceCalculationDto;
import org.jazzteam.eltay.gasimov.dto.ParcelParametersDto;
import org.jazzteam.eltay.gasimov.service.CoefficientForPriceCalculationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@RestController
@Log
public class CoefficientForPriceCalculationController {
    @Autowired
    private CoefficientForPriceCalculationService calculationService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/coefficients")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    CoefficientForPriceCalculationDto addNewCoefficient(@RequestBody @Valid CoefficientForPriceCalculationDto coefficientToSave) throws ObjectNotFoundException {
        return modelMapper.map(calculationService.save(coefficientToSave), CoefficientForPriceCalculationDto.class);
    }

    @GetMapping(path = "/coefficients/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    CoefficientForPriceCalculationDto findById(@PathVariable Long id) throws ObjectNotFoundException {
        return modelMapper.map(calculationService.findOne(id), CoefficientForPriceCalculationDto.class);
    }

    @GetMapping(path = "/coefficients")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<CoefficientForPriceCalculationDto> findAll() throws ObjectNotFoundException {
        return calculationService.findAll().stream()
                .map(coefficient -> modelMapper.map(coefficient, CoefficientForPriceCalculationDto.class))
                .collect(Collectors.toSet());
    }

    @PostMapping(path = "/calculatePrice/{country}")
    public @ResponseBody
    BigDecimal calculatePrice(@PathVariable String country, @RequestBody ParcelParametersDto parametersToCalculate) {
        return calculationService.calculatePrice(parametersToCalculate, country);
    }

    @DeleteMapping(path = "/coefficients/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoefficient(@PathVariable Long id) throws ObjectNotFoundException {
        calculationService.delete(id);
    }

    @PutMapping("/coefficients")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public CoefficientForPriceCalculationDto updateCoefficient(@RequestBody CoefficientForPriceCalculationDto newCoefficient) throws ObjectNotFoundException {
        return modelMapper.map(calculationService.update(newCoefficient), CoefficientForPriceCalculationDto.class);
    }
}
