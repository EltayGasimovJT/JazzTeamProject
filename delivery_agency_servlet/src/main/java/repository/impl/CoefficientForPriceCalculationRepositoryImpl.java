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
        return coefficientForPriceCalculation;
    }

    @Override
    public void delete(CoefficientForPriceCalculation coefficientForPriceCalculation) {
        coefficientForPriceCalculations.remove(coefficientForPriceCalculation);
    }

    @Override
    public List<CoefficientForPriceCalculation> findAll() {
        return coefficientForPriceCalculations;
    }

    @Override
    public CoefficientForPriceCalculation findOne(Long id){
        return coefficientForPriceCalculations.stream()
                .filter(priceCalculationRule -> priceCalculationRule.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public CoefficientForPriceCalculation update(CoefficientForPriceCalculation update){
        CoefficientForPriceCalculation actual = findOne(update.getId());
        coefficientForPriceCalculations.remove(actual);
        actual.setCountry(update.getCountry());
        actual.setCountryCoefficient(update.getCountryCoefficient());
        actual.setParcelSizeLimit(update.getParcelSizeLimit());
        coefficientForPriceCalculations.add(actual);
        return actual;
    }

    @Override
    public CoefficientForPriceCalculation findByCountry(String country) {
        return coefficientForPriceCalculations.stream()
                .filter(priceCalculationRule -> priceCalculationRule.getCountry().equals(country))
                .findFirst()
                .orElse(null);
    }
}
