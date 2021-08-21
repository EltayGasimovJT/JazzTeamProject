package org.jazzteam.eltay.gasimov.controller;

import org.jazzteam.eltay.gasimov.entity.CoefficientForPriceCalculation;
import org.jazzteam.eltay.gasimov.repository.CoefficientForPriceCalculationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
public class CoefficientForPriceCalculationController {
    @Autowired
    private CoefficientForPriceCalculationRepository coefficientRepository;

    @PostMapping(path = "/addCoefficient")
    public @ResponseBody
    String addNewUser(@RequestParam String country
            , @RequestParam Double countryCoefficient
            , @RequestParam Integer parcelSizeLimit
            , Map<String, Object> model) {

        CoefficientForPriceCalculation coefficientToSave = CoefficientForPriceCalculation
                .builder()
                .country(country)
                .countryCoefficient(countryCoefficient)
                .parcelSizeLimit(parcelSizeLimit)
                .build();

        coefficientRepository.save(coefficientToSave);

        List<CoefficientForPriceCalculation> all = coefficientRepository.findAll();
        model.put("coefficients", all);

        return coefficientRepository.findAll().toString();
    }

    @GetMapping(path = "/coefficients")
    public @ResponseBody
    Iterable<CoefficientForPriceCalculation> getAllUsers() {
        return coefficientRepository.findAll();
    }

    @GetMapping(path = "/addCoefficient")
    public ModelAndView getSaved(){
        return new ModelAndView("saved");

    }
}
