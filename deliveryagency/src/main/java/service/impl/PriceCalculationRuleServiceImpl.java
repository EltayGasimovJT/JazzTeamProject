package service.impl;

import entity.Order;
import entity.PriceCalculationRule;
import repository.PriceCalculationRuleRepository;
import repository.impl.PriceCalculationRuleRepositoryImpl;
import service.PriceCalculationRuleService;

import java.math.BigDecimal;
import java.util.List;

public class PriceCalculationRuleServiceImpl implements PriceCalculationRuleService {
    private final PriceCalculationRuleRepository priceCalculationRuleRepository = new PriceCalculationRuleRepositoryImpl();
    private static final int INITIAL_PRISE = 40;

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
        BigDecimal size = BigDecimal.valueOf(getSize(order));
        BigDecimal parcelSizeLimit = BigDecimal.valueOf(priceCalculationRule.getParcelSizeLimit());
        if (size.doubleValue() > parcelSizeLimit.doubleValue()) {
            resultPrice =
                    resultPrice
                            .multiply(BigDecimal.valueOf(priceCalculationRule.getCountryCoefficient())
                                    .multiply(BigDecimal.valueOf(INITIAL_PRISE)
                                            .multiply((size.divide(parcelSizeLimit,1))))
                            );
        } else {
            resultPrice = resultPrice.multiply(BigDecimal.valueOf(
                    priceCalculationRule.getCountryCoefficient() * INITIAL_PRISE));
        }

        return resultPrice;
    }

    @Override
    public PriceCalculationRule findByCountry(String country) {
        return priceCalculationRuleRepository.findByCountry(country);
    }

    private double getSize(Order order) {
        return (order.getParcelParameters().getLength()
                * order.getParcelParameters().getHeight()
                * order.getParcelParameters().getWidth()) + order.getParcelParameters().getWeight();
    }
}
