package repository.impl;

import entity.CoefficientForPrice;
import repository.CoefficientForPriceCalculationRepository;

import java.util.ArrayList;
import java.util.List;

public class CoefficientForPriceCalculationRepositoryImpl implements CoefficientForPriceCalculationRepository {
    private List<CoefficientForPrice> coefficientForPrices = new ArrayList<>();

    @Override
    public CoefficientForPrice save(CoefficientForPrice coefficientForPrice) {
        coefficientForPrices.add(coefficientForPrice);
        return coefficientForPrice;    }

    @Override
    public void delete(CoefficientForPrice coefficientForPrice) {
        coefficientForPrices.remove(coefficientForPrice);
    }

    @Override
    public List<CoefficientForPrice> findAll() {
        return coefficientForPrices;
    }

    @Override
    public CoefficientForPrice findOne(long id) {
        return coefficientForPrices.stream()
                .filter(priceCalculationRule -> priceCalculationRule.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public CoefficientForPrice update(CoefficientForPrice update) {
        for (CoefficientForPrice coefficientForPrice : coefficientForPrices) {
            if (coefficientForPrice.getId() == update.getId()){
                coefficientForPrice.setCountry(update.getCountry());
                coefficientForPrice.setCountryCoefficient(update.getCountryCoefficient());
                coefficientForPrice.setParcelSizeLimit(update.getParcelSizeLimit());
            }
        }
        return update;
    }

    @Override
    public CoefficientForPrice findByCountry(String country) {
        return coefficientForPrices.stream()
                .filter(priceCalculationRule -> priceCalculationRule.getCountry().equals(country))
                .findFirst()
                .orElse(null);
    }
}
