package service.impl;

import dto.CoefficientForPriceCalculationDto;
import dto.OrderDto;
import entity.CoefficientForPriceCalculation;
import entity.Order;
import repository.CoefficientForPriceCalculationRepository;
import repository.impl.CoefficientForPriceCalculationRepositoryImpl;
import service.CoefficientForPriceCalculationService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;

public class CoefficientForPriceCalculationServiceImpl implements CoefficientForPriceCalculationService {
    private final CoefficientForPriceCalculationRepository priceCalculationRuleRepository = new CoefficientForPriceCalculationRepositoryImpl();
    private static final int INITIAL_PRISE = 40;
    private static final int INITIAL_WEIGHT = 20;

    @Override
    public CoefficientForPriceCalculation save(CoefficientForPriceCalculationDto coefficientForPriceCalculationDto) {
        CoefficientForPriceCalculation coefficientForPriceCalculation = CoefficientForPriceCalculation.builder()
                .id(coefficientForPriceCalculationDto.getId())
                .country(coefficientForPriceCalculationDto.getCountry())
                .countryCoefficient(coefficientForPriceCalculationDto.getCountryCoefficient())
                .parcelSizeLimit(coefficientForPriceCalculationDto.getParcelSizeLimit())
                .build();
        return priceCalculationRuleRepository.save(coefficientForPriceCalculation);
    }

    @Override
    public void delete(Long id) {
        priceCalculationRuleRepository.delete(id);
    }

    @Override
    public List<CoefficientForPriceCalculation> findAll() {
        List<CoefficientForPriceCalculation> coefficientsFromRepository = priceCalculationRuleRepository.findAll();
        if (coefficientsFromRepository.isEmpty()) {
            throw new IllegalArgumentException("There is not coefficients in database!!!");
        }
        return coefficientsFromRepository;
    }

    @Override
    public CoefficientForPriceCalculation update(CoefficientForPriceCalculationDto coefficientForPriceCalculationDto) throws SQLException {
        CoefficientForPriceCalculation coefficientForUpdate = CoefficientForPriceCalculation.builder()
                .id(coefficientForPriceCalculationDto.getId())
                .country(coefficientForPriceCalculationDto.getCountry())
                .countryCoefficient(coefficientForPriceCalculationDto.getCountryCoefficient())
                .parcelSizeLimit(coefficientForPriceCalculationDto.getParcelSizeLimit())
                .build();
        return priceCalculationRuleRepository.update(coefficientForUpdate);
    }

    @Override
    public CoefficientForPriceCalculation findOne(long id) {
        final CoefficientForPriceCalculation priceCalculation = priceCalculationRuleRepository.findOne(id);
        if (priceCalculation == null) {
            throw new IllegalArgumentException("There is not coefficient with this Id!!! " + id);
        }
        return priceCalculation;
    }

    @Override
    public BigDecimal calculatePrice(OrderDto order, CoefficientForPriceCalculationDto coefficientForPriceCalculationDto) throws IllegalArgumentException {
        BigDecimal resultPrice = new BigDecimal(1);
        BigDecimal size = BigDecimal.valueOf(getSize(order));
        BigDecimal parcelSizeLimit = BigDecimal.valueOf(coefficientForPriceCalculationDto.getParcelSizeLimit());
        if (size.doubleValue() > parcelSizeLimit.doubleValue() && order.getParcelParameters().getWeight() > INITIAL_WEIGHT) {
            resultPrice =
                    resultPrice
                            .multiply(BigDecimal.valueOf(coefficientForPriceCalculationDto.getCountryCoefficient())
                                    .multiply(BigDecimal.valueOf(INITIAL_PRISE)
                                            .multiply(BigDecimal.valueOf(order.getParcelParameters().getWeight() / INITIAL_WEIGHT))
                                            .multiply((size.divide(parcelSizeLimit, RoundingMode.DOWN))))
                            );
        } else if (size.doubleValue() > parcelSizeLimit.doubleValue()) {
            resultPrice =
                    resultPrice
                            .multiply(BigDecimal.valueOf(coefficientForPriceCalculationDto.getCountryCoefficient())
                                    .multiply(BigDecimal.valueOf(INITIAL_PRISE)
                                            .multiply((size.divide(parcelSizeLimit, RoundingMode.DOWN)))));
        } else if (order.getParcelParameters().getWeight() > INITIAL_WEIGHT) {
            resultPrice = resultPrice.multiply(BigDecimal.valueOf(
                    coefficientForPriceCalculationDto.getCountryCoefficient()
                            * INITIAL_PRISE
                            * (order.getParcelParameters().getWeight() / INITIAL_WEIGHT)));
        } else {
            resultPrice = resultPrice.multiply(BigDecimal.valueOf(
                    coefficientForPriceCalculationDto.getCountryCoefficient() * INITIAL_PRISE));
        }

        return resultPrice;
    }

    @Override
    public CoefficientForPriceCalculation findByCountry(String country) {
        CoefficientForPriceCalculation actualCoefficient = priceCalculationRuleRepository.findByCountry(country);
        if (actualCoefficient == null) {
            throw new IllegalArgumentException("This country is not supported yet!!! " + country);
        }
        return actualCoefficient;
    }

    private double getSize(OrderDto order) {
        return (order.getParcelParameters().getLength()
                * order.getParcelParameters().getHeight()
                * order.getParcelParameters().getWidth()) + order.getParcelParameters().getWeight();
    }
}