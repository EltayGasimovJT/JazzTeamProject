package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.CoefficientForPriceCalculation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoefficientForPriceCalculationRepository extends JpaRepository<CoefficientForPriceCalculation, Long> {
    CoefficientForPriceCalculation findByCountry(String countryForSearch);
}