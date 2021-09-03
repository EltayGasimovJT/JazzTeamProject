package org.jazzteam.eltay.gasimov.controller;

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
public class CoefficientForPriceCalculationController {
    @Autowired
    private CoefficientForPriceCalculationService calculationService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/coefficients")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    CoefficientForPriceCalculationDto addNewCoefficient(@RequestBody @Valid CoefficientForPriceCalculationDto coefficientToSave) {
        return modelMapper.map(calculationService.save(coefficientToSave), CoefficientForPriceCalculationDto.class);
    }

    @GetMapping(path = "/coefficients/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    CoefficientForPriceCalculationDto findById(@PathVariable Long id) {
        return modelMapper.map(calculationService.findOne(id), CoefficientForPriceCalculationDto.class);
    }

    @GetMapping(path = "/coefficients")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<CoefficientForPriceCalculationDto> findAll() {
        return calculationService.findAll().stream()
                .map(coefficient -> modelMapper.map(coefficient, CoefficientForPriceCalculationDto.class))
                .collect(Collectors.toSet());
    }

    @GetMapping(path = "/calculatePrice")
    public @ResponseBody
    BigDecimal calculatePrice(@RequestBody CoefficientForPriceCalculationDto requestCoefficient, @RequestBody ParcelParametersDto parametersToCalculate) {
        return calculationService.calculatePrice(parametersToCalculate, requestCoefficient);
    }

    @DeleteMapping(path = "/coefficients/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoefficient(@PathVariable Long id) {
        calculationService.delete(id);
    }

    @PutMapping("/coefficients")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public CoefficientForPriceCalculationDto updateCoefficient(@RequestBody CoefficientForPriceCalculationDto newCoefficient) {
        return modelMapper.map(calculationService.update(newCoefficient), CoefficientForPriceCalculationDto.class);
    }
}
