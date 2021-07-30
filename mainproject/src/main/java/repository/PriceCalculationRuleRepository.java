package repository;

import entity.PriceCalculationRule;

public interface PriceCalculationRuleRepository  extends GeneralRepository<PriceCalculationRule> {
    PriceCalculationRule findByCountry(String country);
}
