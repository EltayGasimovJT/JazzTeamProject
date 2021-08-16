package service;

import dto.CoefficientForPriceCalculationDto;
import dto.OrderDto;
import entity.CoefficientForPriceCalculation;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface CoefficientForPriceCalculationService {
    CoefficientForPriceCalculation save(CoefficientForPriceCalculationDto coefficientForPriceCalculationDto) throws SQLException;

    void delete(Long id) throws SQLException;

    List<CoefficientForPriceCalculation> findAll() throws SQLException;

    CoefficientForPriceCalculation update(CoefficientForPriceCalculationDto coefficientForPriceCalculationDto) throws SQLException;

    CoefficientForPriceCalculation findOne(long id) throws SQLException;

    BigDecimal calculatePrice(OrderDto order, CoefficientForPriceCalculationDto coefficientForPriceCalculationDto) throws IllegalArgumentException;

    CoefficientForPriceCalculation findByCountry(String country);
}