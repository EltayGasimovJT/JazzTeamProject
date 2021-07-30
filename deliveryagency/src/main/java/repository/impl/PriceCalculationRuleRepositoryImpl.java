package repository.impl;

import entity.Client;
import entity.PriceCalculationRule;
import repository.PriceCalculationRuleRepository;

import java.util.ArrayList;
import java.util.List;

public class PriceCalculationRuleRepositoryImpl implements PriceCalculationRuleRepository {
    private List<PriceCalculationRule> priceCalculationRules = new ArrayList<>();

    @Override
    public PriceCalculationRule save(PriceCalculationRule priceCalculationRule) {
        priceCalculationRules.add(priceCalculationRule);
        return priceCalculationRule;    }

    @Override
    public void delete(PriceCalculationRule priceCalculationRule) {
        priceCalculationRules.remove(priceCalculationRule);
    }

    @Override
    public List<PriceCalculationRule> findAll() {
        return priceCalculationRules;
    }

    @Override
    public PriceCalculationRule findOne(long id) {
        return priceCalculationRules.stream()
                .filter(priceCalculationRule -> priceCalculationRule.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public PriceCalculationRule update(PriceCalculationRule update) {
        for (PriceCalculationRule priceCalculationRule : priceCalculationRules) {
            if (priceCalculationRule.getId() == update.getId()){
                priceCalculationRule.setCountry(update.getCountry());
                priceCalculationRule.setCountryCoefficient(update.getCountryCoefficient());
                priceCalculationRule.setParcelSizeLimit(update.getParcelSizeLimit());
                priceCalculationRule.setInitialParcelPrice(update.getInitialParcelPrice());
            }
        }
        return update;
    }

    @Override
    public PriceCalculationRule findByCountry(String country) {
        return priceCalculationRules.stream()
                .filter(priceCalculationRule -> priceCalculationRule.getCountry() == country)
                .findFirst()
                .orElse(null);
    }
}
