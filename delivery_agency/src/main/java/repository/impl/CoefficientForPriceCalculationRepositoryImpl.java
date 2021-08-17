package repository.impl;

import entity.CoefficientForPriceCalculation;
import repository.CoefficientForPriceCalculationRepository;

import java.util.ArrayList;
import java.util.List;

public class CoefficientForPriceCalculationRepositoryImpl implements CoefficientForPriceCalculationRepository {
    private List<CoefficientForPriceCalculation> coefficientForPriceCalculations = new ArrayList<>();

    @Override
    public CoefficientForPriceCalculation save(CoefficientForPriceCalculation coefficientToSave) {
        coefficientForPriceCalculations.add(coefficientToSave);
        return coefficientToSave;
    }

    @Override
    public void delete(Long idForDelete) {
        coefficientForPriceCalculations.remove(findOne(idForDelete));
    }

    @Override
    public List<CoefficientForPriceCalculation> findAll() {
        return coefficientForPriceCalculations;
    }

    @Override
    public CoefficientForPriceCalculation findOne(Long idForSearch){
        return coefficientForPriceCalculations.stream()
                .filter(priceCalculationRule -> priceCalculationRule.getId().equals(idForSearch))
                .findFirst()
                .orElse(null);
    }

    @Override
    public CoefficientForPriceCalculation update(CoefficientForPriceCalculation newCoefficient){
        CoefficientForPriceCalculation coefficientToUpdate = findOne(newCoefficient.getId());
        coefficientForPriceCalculations.remove(coefficientToUpdate);
        coefficientToUpdate.setCountry(newCoefficient.getCountry());
        coefficientToUpdate.setParcelSizeLimit(newCoefficient.getParcelSizeLimit());
        coefficientToUpdate.setCountryCoefficient(newCoefficient.getCountryCoefficient());
        coefficientForPriceCalculations.add(coefficientToUpdate);
        return coefficientToUpdate;
    }

    @Override
    public CoefficientForPriceCalculation findByCountry(String countryForSearch) {
        return coefficientForPriceCalculations.stream()
                .filter(priceCalculationRule -> priceCalculationRule.getCountry().equals(countryForSearch))
                .findFirst()
                .orElse(null);
    }
}