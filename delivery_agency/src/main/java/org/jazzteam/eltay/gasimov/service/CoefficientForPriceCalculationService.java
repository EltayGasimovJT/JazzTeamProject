package org.jazzteam.eltay.gasimov.service;

import javassist.tools.rmi.ObjectNotFoundException;
import org.jazzteam.eltay.gasimov.dto.CoefficientForPriceCalculationDto;
import org.jazzteam.eltay.gasimov.dto.ParcelParametersDto;
import org.jazzteam.eltay.gasimov.entity.CoefficientForPriceCalculation;

import java.math.BigDecimal;
import java.util.List;

public interface CoefficientForPriceCalculationService {
    CoefficientForPriceCalculation save(CoefficientForPriceCalculationDto coefficientDtoToSave) throws ObjectNotFoundException;

    void delete(Long idForDelete) throws ObjectNotFoundException;

    List<CoefficientForPriceCalculation> findAll() throws ObjectNotFoundException;

    CoefficientForPriceCalculation update(CoefficientForPriceCalculationDto coefficientDtoForUpdate) throws ObjectNotFoundException;

    CoefficientForPriceCalculation findOne(long idForSearch) throws ObjectNotFoundException;

    BigDecimal calculatePrice(ParcelParametersDto parcelParametersDto, CoefficientForPriceCalculationDto coefficientForCalculate) throws IllegalArgumentException;

    CoefficientForPriceCalculation findByCountry(String countryForSearch) throws ObjectNotFoundException;

}