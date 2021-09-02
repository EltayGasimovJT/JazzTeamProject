package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.CoefficientForPriceCalculationDto;
import org.jazzteam.eltay.gasimov.dto.ParcelParametersDto;
import org.jazzteam.eltay.gasimov.entity.CoefficientForPriceCalculation;

import java.math.BigDecimal;
import java.util.List;

public interface CoefficientForPriceCalculationService {
    CoefficientForPriceCalculation save(CoefficientForPriceCalculationDto coefficientDtoToSave);

    void delete(Long idForDelete);

    List<CoefficientForPriceCalculation> findAll();

    CoefficientForPriceCalculation update(CoefficientForPriceCalculationDto coefficientDtoForUpdate);

    CoefficientForPriceCalculation findOne(long idForSearch);

    BigDecimal calculatePrice(ParcelParametersDto parcelParametersDto, CoefficientForPriceCalculationDto coefficientForCalculate) throws IllegalArgumentException;

    CoefficientForPriceCalculation findByCountry(String countryForSearch);

}