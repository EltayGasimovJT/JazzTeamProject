package service;

import entity.CoefficientForPriceCalculation;
import entity.Order;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface CoefficientForPriceCalculationService {
    CoefficientForPriceCalculation addPriceCalculationRule(CoefficientForPriceCalculation coefficientForPriceCalculation) throws SQLException;

    void deletePriceCalculationRule(CoefficientForPriceCalculation coefficientForPriceCalculation);

    List<CoefficientForPriceCalculation> findAllPriceCalculationRules() throws SQLException;

    CoefficientForPriceCalculation update(CoefficientForPriceCalculation coefficientForPriceCalculation) throws SQLException;

    CoefficientForPriceCalculation getCoefficient(long id) throws SQLException;

    BigDecimal calculatePrice(Order order, CoefficientForPriceCalculation coefficientForPriceCalculation) throws IllegalArgumentException;

    CoefficientForPriceCalculation findByCountry(String country);
}
