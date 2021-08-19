package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.CoefficientForPriceCalculation;

public interface CoefficientForPriceCalculationRepository extends GeneralRepository<CoefficientForPriceCalculation> {
    CoefficientForPriceCalculation findByCountry(String countryForSearch);
}