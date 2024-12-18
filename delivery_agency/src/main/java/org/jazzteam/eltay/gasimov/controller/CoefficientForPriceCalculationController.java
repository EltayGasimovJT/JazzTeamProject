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

import static org.jazzteam.eltay.gasimov.util.Constants.*;

@RestController
@Log
public class CoefficientForPriceCalculationController {
    @Autowired
    private CoefficientForPriceCalculationService calculationService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = COEFFICIENTS_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    CoefficientForPriceCalculationDto save(@RequestBody @Valid CoefficientForPriceCalculationDto coefficientToSave) throws ObjectNotFoundException {
        return modelMapper.map(calculationService.save(coefficientToSave), CoefficientForPriceCalculationDto.class);
    }

    @GetMapping(path = COEFFICIENTS_BY_ID_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    CoefficientForPriceCalculationDto findById(@PathVariable Long id) throws ObjectNotFoundException {
        return modelMapper.map(calculationService.findOne(id), CoefficientForPriceCalculationDto.class);
    }

    @GetMapping(path = COEFFICIENTS_URL)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<CoefficientForPriceCalculationDto> findAll() throws ObjectNotFoundException {
        return calculationService.findAll().stream()
                .map(coefficient -> modelMapper.map(coefficient, CoefficientForPriceCalculationDto.class))
                .collect(Collectors.toSet());
    }

    @PostMapping(path = CALCULATE_PRICE_URL)
    public @ResponseBody
    BigDecimal calculatePrice(@PathVariable String country, @RequestBody ParcelParametersDto parametersToCalculate) {
        return calculationService.calculatePrice(parametersToCalculate, country);
    }

    @DeleteMapping(path = COEFFICIENTS_BY_ID_URL)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws ObjectNotFoundException {
        calculationService.delete(id);
    }

    @PutMapping(path = COEFFICIENTS_URL)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public CoefficientForPriceCalculationDto update(@RequestBody CoefficientForPriceCalculationDto newCoefficient) throws ObjectNotFoundException {
        return modelMapper.map(calculationService.update(newCoefficient), CoefficientForPriceCalculationDto.class);
    }
}
