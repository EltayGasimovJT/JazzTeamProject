package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.CoefficientForPriceCalculationDto;
import org.jazzteam.eltay.gasimov.entity.CoefficientForPriceCalculation;
import org.jazzteam.eltay.gasimov.service.CoefficientForPriceCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class CoefficientForPriceCalculationController {
    @Autowired
    private CoefficientForPriceCalculationService calculationService;

    @PostMapping(path = "/coefficients")
    public @ResponseBody
    CoefficientForPriceCalculation addNewCoefficient(@RequestBody CoefficientForPriceCalculationDto coefficient) throws SQLException {

        CoefficientForPriceCalculationDto coefficientToSave = CoefficientForPriceCalculationDto
                .builder()
                .country(coefficient.getCountry())
                .countryCoefficient(coefficient.getCountryCoefficient())
                .parcelSizeLimit(coefficient.getParcelSizeLimit())
                .build();

        return calculationService.save(coefficientToSave);
    }

    @GetMapping(path = "/coefficients")
    public @ResponseBody
    Iterable<CoefficientForPriceCalculation> findAllCoefficients() throws SQLException {
        return calculationService.findAll();
    }

    @DeleteMapping(path = "/coefficients/{id}")
    public void deleteCoefficient(@PathVariable Long id) throws SQLException {
        calculationService.delete(id);
    }

    @PutMapping("/coefficients")
    public CoefficientForPriceCalculation updateCoefficient(@RequestBody CoefficientForPriceCalculationDto newCoefficient) throws SQLException {
        if (calculationService.findOne(newCoefficient.getId()) == null) {
            return calculationService.save(newCoefficient);
        } else {
            CoefficientForPriceCalculationDto coefficientToSave = CoefficientForPriceCalculationDto.builder()
                    .id(newCoefficient.getId())
                    .country(newCoefficient.getCountry())
                    .countryCoefficient(newCoefficient.getCountryCoefficient())
                    .parcelSizeLimit(newCoefficient.getParcelSizeLimit())
                    .build();
            return calculationService.update(coefficientToSave);
        }
    }
}
