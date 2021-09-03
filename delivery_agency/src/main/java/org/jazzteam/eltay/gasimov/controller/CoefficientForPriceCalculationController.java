package org.jazzteam.eltay.gasimov.controller;

import javassist.tools.rmi.ObjectNotFoundException;
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

import static org.jazzteam.eltay.gasimov.controller.constants.ControllerConstant.COEFFICIENTS_BY_ID_URL;
import static org.jazzteam.eltay.gasimov.controller.constants.ControllerConstant.COEFFICIENTS_URL;

@RestController
public class CoefficientForPriceCalculationController {
    @Autowired
    private CoefficientForPriceCalculationService calculationService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = COEFFICIENTS_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    CoefficientForPriceCalculationDto addNewCoefficient(@RequestBody @Valid CoefficientForPriceCalculationDto coefficientToSave) throws ObjectNotFoundException {
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

    @GetMapping(path = COEFFICIENTS_URL)
    public @ResponseBody
    BigDecimal calculatePrice(@RequestBody CoefficientForPriceCalculationDto requestCoefficient, @RequestBody ParcelParametersDto parametersToCalculate) {
        return calculationService.calculatePrice(parametersToCalculate, requestCoefficient);
    }

    @DeleteMapping(path = COEFFICIENTS_BY_ID_URL)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoefficient(@PathVariable Long id) throws ObjectNotFoundException {
        calculationService.delete(id);
    }

    @PutMapping(COEFFICIENTS_URL)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public CoefficientForPriceCalculationDto updateCoefficient(@RequestBody CoefficientForPriceCalculationDto newCoefficient) throws ObjectNotFoundException {
        return modelMapper.map(calculationService.update(newCoefficient), CoefficientForPriceCalculationDto.class);
    }
}