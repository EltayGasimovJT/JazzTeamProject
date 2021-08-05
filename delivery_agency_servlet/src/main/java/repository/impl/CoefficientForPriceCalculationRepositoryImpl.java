package repository.impl;

import entity.CoefficientForPriceCalculation;
import repository.CoefficientForPriceCalculationRepository;

import java.util.ArrayList;
import java.util.List;

public class CoefficientForPriceCalculationRepositoryImpl implements CoefficientForPriceCalculationRepository {
    private List<CoefficientForPriceCalculation> coefficientForPriceCalculations = new ArrayList<>();

    @Override
    public CoefficientForPriceCalculation save(CoefficientForPriceCalculation coefficientForPriceCalculation) {
        coefficientForPriceCalculations.add(coefficientForPriceCalculation);
        return coefficientForPriceCalculation;    }

    @Override
    public void delete(CoefficientForPriceCalculation coefficientForPriceCalculation) {
        coefficientForPriceCalculations.remove(coefficientForPriceCalculation);
    }

    @Override
    public List<CoefficientForPriceCalculation> findAll() {
        return coefficientForPriceCalculations;
    }

    @Override
    public CoefficientForPriceCalculation findOne(Long id) {
        return coefficientForPriceCalculations.stream()
                .filter(priceCalculationRule -> priceCalculationRule.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public CoefficientForPriceCalculation update(CoefficientForPriceCalculation update) {
        for (CoefficientForPriceCalculation coefficientForPriceCalculation : coefficientForPriceCalculations) {
            if (coefficientForPriceCalculation.getId() == update.getId()){
                coefficientForPriceCalculation.setCountry(update.getCountry());
                coefficientForPriceCalculation.setCountryCoefficient(update.getCountryCoefficient());
                coefficientForPriceCalculation.setParcelSizeLimit(update.getParcelSizeLimit());
            }
        }
        return update;
    }

    @Override
    public CoefficientForPriceCalculation findByCountry(String country) {
        return coefficientForPriceCalculations.stream()
                .filter(priceCalculationRule -> priceCalculationRule.getCountry().equals(country))
                .findFirst()
                .orElse(null);
    }
}
