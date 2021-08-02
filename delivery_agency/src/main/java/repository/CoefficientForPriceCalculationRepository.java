package repository;

import entity.CoefficientForPrice;

public interface CoefficientForPriceCalculationRepository extends GeneralRepository<CoefficientForPrice> {
    CoefficientForPrice findByCountry(String country);
}
