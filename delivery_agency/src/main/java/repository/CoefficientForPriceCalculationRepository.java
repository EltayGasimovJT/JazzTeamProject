package repository;

import entity.CoefficientForPriceCalculation;

public interface CoefficientForPriceCalculationRepository extends GeneralRepository<CoefficientForPriceCalculation> {
    CoefficientForPriceCalculation findByCountry(String countryForSearch);
}
