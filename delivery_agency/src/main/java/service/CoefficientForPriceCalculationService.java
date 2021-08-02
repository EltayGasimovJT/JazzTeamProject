package service;

import entity.CoefficientForPriceCalculation;
import entity.Order;

import java.math.BigDecimal;
import java.util.List;

public interface CoefficientForPriceCalculationService {
    CoefficientForPriceCalculation addPriceCalculationRule(CoefficientForPriceCalculation coefficientForPriceCalculation);

    void deletePriceCalculationRule(CoefficientForPriceCalculation coefficientForPriceCalculation);

    List<CoefficientForPriceCalculation> findAllPriceCalculationRules();

    CoefficientForPriceCalculation update(CoefficientForPriceCalculation coefficientForPriceCalculation);

    CoefficientForPriceCalculation getRule(long id);

    BigDecimal calculatePrice(Order order, CoefficientForPriceCalculation coefficientForPriceCalculation) throws IllegalArgumentException;

    CoefficientForPriceCalculation findByCountry(String country);
}
