package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.CoefficientForPriceCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoefficientForPriceCalculationRepository extends JpaRepository<CoefficientForPriceCalculation, Long> {
    CoefficientForPriceCalculation findByCountry(String countryForSearch);
}