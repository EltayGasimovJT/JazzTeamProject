package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.dto.CoefficientForPriceCalculationDto;
import org.jazzteam.eltay.gasimov.entity.CoefficientForPriceCalculation;
import org.jazzteam.eltay.gasimov.service.CoefficientForPriceCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
public class CoefficientForPriceCalculationController {
    @Autowired
    private CoefficientForPriceCalculationService calculationService;

    @PostMapping(path = "/addCoefficient")
    public @ResponseBody
    String addNewUser(@RequestParam String country
            , @RequestParam Double countryCoefficient
            , @RequestParam Integer parcelSizeLimit
            , Map<String, Object> model) throws SQLException {

        CoefficientForPriceCalculationDto coefficientToSave = CoefficientForPriceCalculationDto
                .builder()
                .country(country)
                .countryCoefficient(countryCoefficient)
                .parcelSizeLimit(parcelSizeLimit)
                .build();

        calculationService.save(coefficientToSave);

        List<CoefficientForPriceCalculation> all = calculationService.findAll();
        model.put("coefficients", all);

        return calculationService.findAll().toString();
    }

    @GetMapping(path = "/coefficients")
    public @ResponseBody
    Iterable<CoefficientForPriceCalculation> getAllUsers() throws SQLException {
        return calculationService.findAll();
    }

    @GetMapping(path = "/addCoefficient")
    public ModelAndView getSaved(){
        return new ModelAndView("saved");

    }
}
