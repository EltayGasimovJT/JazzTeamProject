package org.jazzteam.eltay.gasimov.service.impl;

import org.jazzteam.eltay.gasimov.dto.CoefficientForPriceCalculationDto;
import org.jazzteam.eltay.gasimov.dto.ParcelParametersDto;
import org.jazzteam.eltay.gasimov.entity.CoefficientForPriceCalculation;
import org.jazzteam.eltay.gasimov.repository.CoefficientForPriceCalculationRepository;
import org.jazzteam.eltay.gasimov.service.CoefficientForPriceCalculationService;
import org.jazzteam.eltay.gasimov.validator.CoefficientForPriseCalculationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service(value = "coefficientForPriceCalculationService")
public class CoefficientForPriceCalculationServiceImpl implements CoefficientForPriceCalculationService {
    @Autowired
    private CoefficientForPriceCalculationRepository priceCalculationRuleRepository;

    private static final int INITIAL_PRISE = 1400;
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
        Optional<CoefficientForPriceCalculation> foundCoefficientOptional = priceCalculationRuleRepository.findById(idForDelete);
        CoefficientForPriceCalculation foundCoefficient = foundCoefficientOptional.orElseGet(CoefficientForPriceCalculation::new);
        CoefficientForPriseCalculationValidator.validateCoefficient(foundCoefficient);
        priceCalculationRuleRepository.delete(foundCoefficient);
    }

    @Override
    public List<CoefficientForPriceCalculation> findAll() throws IllegalArgumentException {
        List<CoefficientForPriceCalculation> coefficientsFromRepository = priceCalculationRuleRepository.findAll();
        CoefficientForPriseCalculationValidator.validateCoefficientList(coefficientsFromRepository);
        return coefficientsFromRepository;
    }

    @Override
    public CoefficientForPriceCalculation update(CoefficientForPriceCalculationDto coefficientDtoForUpdate) throws IllegalArgumentException {
        Optional<CoefficientForPriceCalculation> foundCoefficientOptional = priceCalculationRuleRepository.findById(coefficientDtoForUpdate.getId());
        CoefficientForPriceCalculation foundCoefficient = foundCoefficientOptional.orElseGet(CoefficientForPriceCalculation::new);
        CoefficientForPriseCalculationValidator.validateCoefficient(foundCoefficient);

        CoefficientForPriceCalculation coefficientForUpdate = CoefficientForPriceCalculation.builder()
                .id(coefficientDtoForUpdate.getId())
                .country(coefficientDtoForUpdate.getCountry())
                .countryCoefficient(coefficientDtoForUpdate.getCountryCoefficient())
                .parcelSizeLimit(coefficientDtoForUpdate.getParcelSizeLimit())
                .build();

        return priceCalculationRuleRepository.save(coefficientForUpdate);
    }

    @Override
    public CoefficientForPriceCalculation findOne(long idForSearch) throws IllegalArgumentException {
        Optional<CoefficientForPriceCalculation> foundCoefficientOptional = priceCalculationRuleRepository.findById(idForSearch);
        CoefficientForPriceCalculation foundCoefficient = foundCoefficientOptional.orElseGet(CoefficientForPriceCalculation::new);

        CoefficientForPriseCalculationValidator.validateCoefficient(foundCoefficient);

        return foundCoefficient;
    }

    @Override
    public BigDecimal calculatePrice(ParcelParametersDto parcelParametersDto, CoefficientForPriceCalculationDto coefficientForCalculate) throws IllegalArgumentException {
        BigDecimal resultPrice = new BigDecimal(1);
        BigDecimal size = BigDecimal.valueOf(getSize(parcelParametersDto));
        BigDecimal parcelSizeLimit = BigDecimal.valueOf(coefficientForCalculate.getParcelSizeLimit());
        if (size.doubleValue() > parcelSizeLimit.doubleValue() && parcelParametersDto.getWeight() > INITIAL_WEIGHT) {
            resultPrice =
                    resultPrice
                            .multiply(BigDecimal.valueOf(coefficientForCalculate.getCountryCoefficient())
                                    .multiply(BigDecimal.valueOf(INITIAL_PRISE)
                                            .multiply(BigDecimal.valueOf(parcelParametersDto.getWeight() / INITIAL_WEIGHT))
                                            .multiply((size.divide(parcelSizeLimit, RoundingMode.DOWN))))
                            );
        } else if (size.doubleValue() > parcelSizeLimit.doubleValue()) {
            resultPrice =
                    resultPrice
                            .multiply(BigDecimal.valueOf(coefficientForCalculate.getCountryCoefficient())
                                    .multiply(BigDecimal.valueOf(INITIAL_PRISE)
                                            .multiply((size.divide(parcelSizeLimit, RoundingMode.DOWN)))));
        } else if (parcelParametersDto.getWeight() > INITIAL_WEIGHT) {
            resultPrice = resultPrice.multiply(BigDecimal.valueOf(
                    coefficientForCalculate.getCountryCoefficient()
                            * INITIAL_PRISE
                            * (parcelParametersDto.getWeight() / INITIAL_WEIGHT)));
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

    private double getSize(ParcelParametersDto parcelParametersDto) {
        return (parcelParametersDto.getLength()
                * parcelParametersDto.getHeight()
                * parcelParametersDto.getWidth()) + parcelParametersDto.getWeight();
    }
}