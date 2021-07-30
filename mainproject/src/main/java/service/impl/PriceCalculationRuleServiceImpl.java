package service.impl;

import entity.Order;
import entity.PriceCalculationRule;
import repository.PriceCalculationRuleRepository;
import repository.impl.PriceCalculationRuleRepositoryImpl;
import service.PriceCalculationRuleService;

import java.math.BigDecimal;
import java.util.List;

public class PriceCalculationRuleServiceImpl implements PriceCalculationRuleService {
    private PriceCalculationRuleRepository priceCalculationRuleRepository = new PriceCalculationRuleRepositoryImpl();

    @Override
    public PriceCalculationRule addPriceCalculationRule(PriceCalculationRule priceCalculationRule) {
        return priceCalculationRuleRepository.save(priceCalculationRule);
    }

    @Override
    public void deletePriceCalculationRule(PriceCalculationRule priceCalculationRule) {
        priceCalculationRuleRepository.delete(priceCalculationRule);
    }

    @Override
    public List<PriceCalculationRule> findAllPriceCalculationRules() {
        return priceCalculationRuleRepository.findAll();
    }

    @Override
    public PriceCalculationRule getOPriceCalculationRule(long id) {
        return priceCalculationRuleRepository.findOne(id);
    }

    @Override
    public PriceCalculationRule update(PriceCalculationRule priceCalculationRule) {
        return priceCalculationRuleRepository.update(priceCalculationRule);
    }

    @Override
    public PriceCalculationRule getRule(long id) {
        return priceCalculationRuleRepository.findOne(id);
    }

    @Override
    public BigDecimal calculatePrice(Order order, PriceCalculationRule priceCalculationRule) throws IllegalArgumentException {
        BigDecimal resultPrice = new BigDecimal(1);
        double size = getSize(order);
        if (size > priceCalculationRule.getParcelSizeLimit()) {
            resultPrice.multiply(BigDecimal.valueOf(
                    priceCalculationRule.getCountryCoefficient() * priceCalculationRule.getInitialParcelPrice()
                            * size / priceCalculationRule.getParcelSizeLimit()));
        } else {
            resultPrice.multiply(BigDecimal.valueOf(
                    priceCalculationRule.getCountryCoefficient() * priceCalculationRule.getInitialParcelPrice()));
        }

        return resultPrice;
    }

    private double getSize(Order order) {
        return order.getParcelParameters().getLength()
                * order.getParcelParameters().getHeight()
                * order.getParcelParameters().getWidth();
    }
}
