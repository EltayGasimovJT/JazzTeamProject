package service;

import dto.CoefficientForPriceCalculationDto;
import dto.OrderDto;
import entity.CoefficientForPriceCalculation;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface CoefficientForPriceCalculationService {
    CoefficientForPriceCalculation save(CoefficientForPriceCalculationDto coefficientDtoToSave) throws SQLException;

    void delete(Long idForDelete) throws SQLException;

    List<CoefficientForPriceCalculation> findAll() throws SQLException;

    CoefficientForPriceCalculation update(CoefficientForPriceCalculationDto coefficientDtoForUpdate) throws SQLException;

    CoefficientForPriceCalculation findOne(long idForSearch) throws SQLException;

    BigDecimal calculatePrice(OrderDto order, CoefficientForPriceCalculationDto coefficientForCalculate) throws IllegalArgumentException;

    CoefficientForPriceCalculation findByCountry(String countryForSearch);
}