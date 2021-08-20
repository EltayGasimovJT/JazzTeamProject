package org.jazzteam.eltay.gasimov.service.impl;

import org.jazzteam.eltay.gasimov.dto.CoefficientForPriceCalculationDto;
import org.jazzteam.eltay.gasimov.dto.OrderDto;
import org.jazzteam.eltay.gasimov.entity.CoefficientForPriceCalculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jazzteam.eltay.gasimov.repository.CoefficientForPriceCalculationRepository;
import org.jazzteam.eltay.gasimov.service.CoefficientForPriceCalculationService;
import org.jazzteam.eltay.gasimov.validator.CoefficientForPriseCalculationValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;

@Service(value = "coefficientForPriceCalculationService")
public class CoefficientForPriceCalculationServiceImpl implements CoefficientForPriceCalculationService {
    @Autowired
    private CoefficientForPriceCalculationRepository priceCalculationRuleRepository;

    private static final int INITIAL_PRISE = 40;
    private static final int INITIAL_WEIGHT = 20;

    @Override
    public CoefficientForPriceCalculation save(CoefficientForPriceCalculationDto coefficientDtoToSave) throws IllegalArgumentException {
        CoefficientForPriceCalculation coefficientToSave = CoefficientForPriceCalculation.builder()
                .id(coefficientDtoToSave.getId())
                .country(coefficientDtoToSave.getCountry())
                .countryCoefficient(coefficientDtoToSave.getCountryCoefficient())
                .parcelSizeLimit(coefficientDtoToSave.getParcelSizeLimit())
                .build();

        CoefficientForPriseCalculationValidator.validateOnSave(coefficientToSave);

        return priceCalculationRuleRepository.save(coefficientToSave);
    }

    @Override
    public void delete(Long idForDelete) throws IllegalArgumentException {
        CoefficientForPriseCalculationValidator.validateCoefficient(priceCalculationRuleRepository.findOne(idForDelete));
        priceCalculationRuleRepository.delete(idForDelete);
    }

    @Override
    public List<CoefficientForPriceCalculation> findAll() throws IllegalArgumentException {
        List<CoefficientForPriceCalculation> coefficientsFromRepository = priceCalculationRuleRepository.findAll();
        CoefficientForPriseCalculationValidator.validateCoefficientList(coefficientsFromRepository);
        return coefficientsFromRepository;
    }

    @Override
    public CoefficientForPriceCalculation update(CoefficientForPriceCalculationDto coefficientDtoForUpdate) throws SQLException, IllegalArgumentException {
        CoefficientForPriseCalculationValidator.validateCoefficient(priceCalculationRuleRepository.findOne(coefficientDtoForUpdate.getId()));

        CoefficientForPriceCalculation coefficientForUpdate = CoefficientForPriceCalculation.builder()
                .id(coefficientDtoForUpdate.getId())
                .country(coefficientDtoForUpdate.getCountry())
                .countryCoefficient(coefficientDtoForUpdate.getCountryCoefficient())
                .parcelSizeLimit(coefficientDtoForUpdate.getParcelSizeLimit())
                .build();

        return priceCalculationRuleRepository.update(coefficientForUpdate);
    }

    @Override
    public CoefficientForPriceCalculation findOne(long idForSearch) throws IllegalArgumentException {
        CoefficientForPriceCalculation foundCoefficient = priceCalculationRuleRepository.findOne(idForSearch);

        CoefficientForPriseCalculationValidator.validateCoefficient(foundCoefficient);

        return foundCoefficient;
    }

    @Override
    public BigDecimal calculatePrice(OrderDto order, CoefficientForPriceCalculationDto coefficientForCalculate) throws IllegalArgumentException {
        BigDecimal resultPrice = new BigDecimal(1);
        BigDecimal size = BigDecimal.valueOf(getSize(order));
        BigDecimal parcelSizeLimit = BigDecimal.valueOf(coefficientForCalculate.getParcelSizeLimit());
        if (size.doubleValue() > parcelSizeLimit.doubleValue() && order.getParcelParameters().getWeight() > INITIAL_WEIGHT) {
            resultPrice =
                    resultPrice
                            .multiply(BigDecimal.valueOf(coefficientForCalculate.getCountryCoefficient())
                                    .multiply(BigDecimal.valueOf(INITIAL_PRISE)
                                            .multiply(BigDecimal.valueOf(order.getParcelParameters().getWeight() / INITIAL_WEIGHT))
                                            .multiply((size.divide(parcelSizeLimit, RoundingMode.DOWN))))
                            );
        } else if (size.doubleValue() > parcelSizeLimit.doubleValue()) {
            resultPrice =
                    resultPrice
                            .multiply(BigDecimal.valueOf(coefficientForCalculate.getCountryCoefficient())
                                    .multiply(BigDecimal.valueOf(INITIAL_PRISE)
                                            .multiply((size.divide(parcelSizeLimit, RoundingMode.DOWN)))));
        } else if (order.getParcelParameters().getWeight() > INITIAL_WEIGHT) {
            resultPrice = resultPrice.multiply(BigDecimal.valueOf(
                    coefficientForCalculate.getCountryCoefficient()
                            * INITIAL_PRISE
                            * (order.getParcelParameters().getWeight() / INITIAL_WEIGHT)));
        } else {
            resultPrice = resultPrice.multiply(BigDecimal.valueOf(
                    coefficientForCalculate.getCountryCoefficient() * INITIAL_PRISE));
        }

        return resultPrice;
    }

    @Override
    public CoefficientForPriceCalculation findByCountry(String countryForSearch) throws IllegalArgumentException {
        CoefficientForPriceCalculation foundCoefficient = priceCalculationRuleRepository.findByCountry(countryForSearch);
        CoefficientForPriseCalculationValidator.validateCoefficient(foundCoefficient);

        return foundCoefficient;
    }

    @Override
    public void clear() {
        priceCalculationRuleRepository.clear();
    }

    private double getSize(OrderDto order) {
        return (order.getParcelParameters().getLength()
                * order.getParcelParameters().getHeight()
                * order.getParcelParameters().getWidth()) + order.getParcelParameters().getWeight();
    }
}