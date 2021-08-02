package service.impl;

import entity.CoefficientForPriceCalculation;
import entity.Order;
import repository.CoefficientForPriceCalculationRepository;
import repository.impl.CoefficientForPriceCalculationRepositoryImpl;
import service.CoefficientForPriceCalculationService;

import java.math.BigDecimal;
import java.util.List;

public class CoefficientForPriceCalculationServiceImpl implements CoefficientForPriceCalculationService {
    private final CoefficientForPriceCalculationRepository priceCalculationRuleRepository = new CoefficientForPriceCalculationRepositoryImpl();
    private static final int INITIAL_PRISE = 40;
    private static final int INITIAL_WEIGHT = 20;

    @Override
    public CoefficientForPriceCalculation addPriceCalculationRule(CoefficientForPriceCalculation coefficientForPriceCalculation) {
        return priceCalculationRuleRepository.save(coefficientForPriceCalculation);
    }

    @Override
    public void deletePriceCalculationRule(CoefficientForPriceCalculation coefficientForPriceCalculation) {
        priceCalculationRuleRepository.delete(coefficientForPriceCalculation);
    }

    @Override
    public List<CoefficientForPriceCalculation> findAllPriceCalculationRules() {
        return priceCalculationRuleRepository.findAll();
    }

    @Override
    public CoefficientForPriceCalculation update(CoefficientForPriceCalculation coefficientForPriceCalculation) {
        return priceCalculationRuleRepository.update(coefficientForPriceCalculation);
    }

    @Override
    public CoefficientForPriceCalculation getCoefficient(long id) {
        return priceCalculationRuleRepository.findOne(id);
    }

    @Override
    public BigDecimal calculatePrice(Order order, CoefficientForPriceCalculation coefficientForPriceCalculation) throws IllegalArgumentException {
        BigDecimal resultPrice = new BigDecimal(1);
        BigDecimal size = BigDecimal.valueOf(getSize(order));
        BigDecimal parcelSizeLimit = BigDecimal.valueOf(coefficientForPriceCalculation.getParcelSizeLimit());
        if (size.doubleValue() > parcelSizeLimit.doubleValue() && order.getParcelParameters().getWeight() > INITIAL_WEIGHT) {
            resultPrice =
                    resultPrice
                            .multiply(BigDecimal.valueOf(coefficientForPriceCalculation.getCountryCoefficient())
                                    .multiply(BigDecimal.valueOf(INITIAL_PRISE)
                                            .multiply(BigDecimal.valueOf(order.getParcelParameters().getWeight() / INITIAL_WEIGHT))
                                            .multiply((size.divide(parcelSizeLimit, 1))))
                            );
        } else if (size.doubleValue() > parcelSizeLimit.doubleValue()) {
            resultPrice =
                    resultPrice
                            .multiply(BigDecimal.valueOf(coefficientForPriceCalculation.getCountryCoefficient())
                                    .multiply(BigDecimal.valueOf(INITIAL_PRISE)
                                            .multiply((size.divide(parcelSizeLimit, 1)))));
        } else if (order.getParcelParameters().getWeight() > INITIAL_WEIGHT) {
            resultPrice = resultPrice.multiply(BigDecimal.valueOf(
                    coefficientForPriceCalculation.getCountryCoefficient()
                            * INITIAL_PRISE
                            * (order.getParcelParameters().getWeight() / INITIAL_WEIGHT)));
        } else {
            resultPrice = resultPrice.multiply(BigDecimal.valueOf(
                    coefficientForPriceCalculation.getCountryCoefficient() * INITIAL_PRISE));
        }

        return resultPrice;
    }

    @Override
    public CoefficientForPriceCalculation findByCountry(String country) {
        return priceCalculationRuleRepository.findByCountry(country);
    }

    private double getSize(Order order) {
        return (order.getParcelParameters().getLength()
                * order.getParcelParameters().getHeight()
                * order.getParcelParameters().getWidth()) + order.getParcelParameters().getWeight();
    }
}
