package service;

import entity.Order;
import entity.CoefficientForPrice;

import java.math.BigDecimal;
import java.util.List;

public interface CoefficientForPriceCalculationService {
    CoefficientForPrice addPriceCalculationRule(CoefficientForPrice coefficientForPrice);

    void deletePriceCalculationRule(CoefficientForPrice coefficientForPrice);

    List<CoefficientForPrice> findAllPriceCalculationRules();

    CoefficientForPrice update(CoefficientForPrice coefficientForPrice);

    CoefficientForPrice getRule(long id);

    BigDecimal calculatePrice(Order order, CoefficientForPrice coefficientForPrice) throws IllegalArgumentException;

    CoefficientForPrice findByCountry(String country);
}
