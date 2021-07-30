package service;

import com.sun.scenario.effect.impl.prism.PrCropPeer;
import entity.Order;
import entity.PriceCalculationRule;

import java.math.BigDecimal;
import java.util.List;

public interface PriceCalculationRuleService {
    PriceCalculationRule addPriceCalculationRule(PriceCalculationRule priceCalculationRule);

    void deletePriceCalculationRule(PriceCalculationRule priceCalculationRule);

    List<PriceCalculationRule> findAllPriceCalculationRules();

    PriceCalculationRule getOPriceCalculationRule(long id);

    PriceCalculationRule update(PriceCalculationRule priceCalculationRule);

    PriceCalculationRule getRule(long id);

    BigDecimal calculatePrice(Order order, PriceCalculationRule priceCalculationRule) throws IllegalArgumentException;

}
