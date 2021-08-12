package service;

import dto.CoefficientForPriceCalculationDto;
import entity.Order;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface CoefficientForPriceCalculationService {
    CoefficientForPriceCalculationDto addPriceCalculationRule(CoefficientForPriceCalculationDto coefficientForPriceCalculationDto) throws SQLException;

    void deletePriceCalculationRule(Long id) throws SQLException;

    List<CoefficientForPriceCalculationDto> findAllPriceCalculationRules() throws SQLException;

    CoefficientForPriceCalculationDto update(CoefficientForPriceCalculationDto coefficientForPriceCalculationDto) throws SQLException;

    CoefficientForPriceCalculationDto getCoefficient(long id) throws SQLException;

    BigDecimal calculatePrice(Order order, CoefficientForPriceCalculationDto coefficientForPriceCalculationDto) throws IllegalArgumentException;

    CoefficientForPriceCalculationDto findByCountry(String country);
}