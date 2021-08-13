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
    public void delete(Long id) {
        coefficientForPriceCalculations.remove(findOne(id));
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
        CoefficientForPriceCalculation coefficientForPriceCalculation = findOne(update.getId());
        coefficientForPriceCalculations.remove(coefficientForPriceCalculation);
        coefficientForPriceCalculation.setCountry(update.getCountry());
        coefficientForPriceCalculation.setParcelSizeLimit(update.getParcelSizeLimit());
        coefficientForPriceCalculation.setCountryCoefficient(update.getCountryCoefficient());
        coefficientForPriceCalculations.add(coefficientForPriceCalculation);
        return coefficientForPriceCalculation;
    }

    @Override
    public CoefficientForPriceCalculation findByCountry(String country) {
        return coefficientForPriceCalculations.stream()
                .filter(priceCalculationRule -> priceCalculationRule.getCountry().equals(country))
                .findFirst()
                .orElse(null);
    }
}