package service.impl;

import entity.Order;
import entity.CoefficientForPrice;
import repository.CoefficientForPriceCalculationRepository;
import repository.impl.CoefficientForPriceCalculationRepositoryImpl;
import service.CoefficientForPriceCalculationService;

import java.math.BigDecimal;
import java.util.List;

public class CoefficientForPriceCalculationServiceImpl implements CoefficientForPriceCalculationService {
    private final CoefficientForPriceCalculationRepository priceCalculationRuleRepository = new CoefficientForPriceCalculationRepositoryImpl();
    private static final int INITIAL_PRISE = 40;

    @Override
    public CoefficientForPrice addPriceCalculationRule(CoefficientForPrice coefficientForPrice) {
        return priceCalculationRuleRepository.save(coefficientForPrice);
    }

    @Override
    public void deletePriceCalculationRule(CoefficientForPrice coefficientForPrice) {
        priceCalculationRuleRepository.delete(coefficientForPrice);
    }

    @Override
    public List<CoefficientForPrice> findAllPriceCalculationRules() {
        return priceCalculationRuleRepository.findAll();
    }

    @Override
    public CoefficientForPrice update(CoefficientForPrice coefficientForPrice) {
        return priceCalculationRuleRepository.update(coefficientForPrice);
    }

    @Override
    public CoefficientForPrice getRule(long id) {
        return priceCalculationRuleRepository.findOne(id);
    }

    @Override
    public BigDecimal calculatePrice(Order order, CoefficientForPrice coefficientForPrice) throws IllegalArgumentException {
        BigDecimal resultPrice = new BigDecimal(1);
        BigDecimal size = BigDecimal.valueOf(getSize(order));
        BigDecimal parcelSizeLimit = BigDecimal.valueOf(coefficientForPrice.getParcelSizeLimit());
        if (size.doubleValue() > parcelSizeLimit.doubleValue()) {
            resultPrice =
                    resultPrice
                            .multiply(BigDecimal.valueOf(coefficientForPrice.getCountryCoefficient())
                                    .multiply(BigDecimal.valueOf(INITIAL_PRISE)
                                            .multiply((size.divide(parcelSizeLimit,1))))
                            );
        } else {
            resultPrice = resultPrice.multiply(BigDecimal.valueOf(
                    coefficientForPrice.getCountryCoefficient() * INITIAL_PRISE));
        }

        return resultPrice;
    }

    @Override
    public CoefficientForPrice findByCountry(String country) {
        return priceCalculationRuleRepository.findByCountry(country);
    }

    private double getSize(Order order) {
        return (order.getParcelParameters().getLength()
                * order.getParcelParameters().getHeight()
                * order.getParcelParameters().getWidth()) + order.getParcelParameters().getWeight();
    }
}
