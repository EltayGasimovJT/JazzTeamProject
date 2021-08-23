package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.CoefficientForPriceCalculationDto;
import org.jazzteam.eltay.gasimov.service.CoefficientForPriceCalculationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.stream.Collectors;

@RestController
public class CoefficientForPriceCalculationController {
    @Autowired
    private CoefficientForPriceCalculationService calculationService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/coefficients")
    public @ResponseBody
    CoefficientForPriceCalculationDto addNewCoefficient(@RequestBody CoefficientForPriceCalculationDto coefficient) throws SQLException {

        CoefficientForPriceCalculationDto coefficientToSave = CoefficientForPriceCalculationDto
                .builder()
                .country(coefficient.getCountry())
                .countryCoefficient(coefficient.getCountryCoefficient())
                .parcelSizeLimit(coefficient.getParcelSizeLimit())
                .build();

        return modelMapper.map(calculationService.save(coefficientToSave), CoefficientForPriceCalculationDto.class);
    }

    @GetMapping(path = "/coefficients/{id}")
    public @ResponseBody
    CoefficientForPriceCalculationDto findById(@PathVariable Long id) throws SQLException {
        return modelMapper.map(calculationService.findOne(id), CoefficientForPriceCalculationDto.class);
    }

    @GetMapping(path = "/coefficients")
    public @ResponseBody
    Iterable<CoefficientForPriceCalculationDto> findAll() throws SQLException {
        return calculationService.findAll().stream()
                .map(coefficient -> modelMapper.map(coefficient, CoefficientForPriceCalculationDto.class))
                .collect(Collectors.toSet());
    }

    @DeleteMapping(path = "/coefficients/{id}")
    public void deleteCoefficient(@PathVariable Long id) throws SQLException {
        calculationService.delete(id);
    }

    @PutMapping("/coefficients")
    public CoefficientForPriceCalculationDto updateCoefficient(@RequestBody CoefficientForPriceCalculationDto newCoefficient) throws SQLException {
        if (calculationService.findOne(newCoefficient.getId()) == null) {
            return modelMapper.map(calculationService.save(newCoefficient), CoefficientForPriceCalculationDto.class);
        } else {
            CoefficientForPriceCalculationDto coefficientToSave = CoefficientForPriceCalculationDto.builder()
                    .id(newCoefficient.getId())
                    .country(newCoefficient.getCountry())
                    .countryCoefficient(newCoefficient.getCountryCoefficient())
                    .parcelSizeLimit(newCoefficient.getParcelSizeLimit())
                    .build();
            return modelMapper.map(calculationService.update(coefficientToSave), CoefficientForPriceCalculationDto.class);
        }
    }
}
