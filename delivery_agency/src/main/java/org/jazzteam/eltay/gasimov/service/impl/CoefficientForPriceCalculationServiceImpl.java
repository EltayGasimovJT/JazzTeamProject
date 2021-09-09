package org.jazzteam.eltay.gasimov.service.impl;

import javassist.tools.rmi.ObjectNotFoundException;
import org.jazzteam.eltay.gasimov.dto.CoefficientForPriceCalculationDto;
import org.jazzteam.eltay.gasimov.dto.ParcelParametersDto;
import org.jazzteam.eltay.gasimov.entity.CoefficientForPriceCalculation;
import org.jazzteam.eltay.gasimov.repository.CoefficientForPriceCalculationRepository;
import org.jazzteam.eltay.gasimov.service.CoefficientForPriceCalculationService;
import org.jazzteam.eltay.gasimov.validator.CoefficientForPriseCalculationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service(value = "coefficientForPriceCalculationService")
public class CoefficientForPriceCalculationServiceImpl implements CoefficientForPriceCalculationService {
    @Autowired
    private CoefficientForPriceCalculationRepository priceCalculationRuleRepository;

    private static final int INITIAL_WEIGHT = 50000;
    private static final double PRICE_FOR_PACKAGE = 1.9;
    private static final double COEFFICIENT_FOR_KILOGRAMS_CONVERT = 1000;
    private static final double PRICE_FOR_DELIVERY = 3.25;
    private static final double WEIGHT_COEFFICIENT = 0.5;
    private static final double VOLUME_COEFFICIENT = 0.10;
    private static final double INITIAL_COUNTRY_COEFFICIENT = 0.8;

    @Override
    public CoefficientForPriceCalculation save(CoefficientForPriceCalculationDto coefficientDtoToSave) throws IllegalArgumentException, ObjectNotFoundException {
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
    public void delete(Long idForDelete) throws IllegalArgumentException, ObjectNotFoundException {
        Optional<CoefficientForPriceCalculation> foundCoefficientOptional = priceCalculationRuleRepository.findById(idForDelete);
        CoefficientForPriceCalculation foundCoefficient = foundCoefficientOptional.orElseGet(CoefficientForPriceCalculation::new);
        CoefficientForPriseCalculationValidator.validateCoefficient(foundCoefficient);
        priceCalculationRuleRepository.delete(foundCoefficient);
    }

    @Override
    public List<CoefficientForPriceCalculation> findAll() throws IllegalArgumentException, ObjectNotFoundException {
        List<CoefficientForPriceCalculation> coefficientsFromRepository = priceCalculationRuleRepository.findAll();
        CoefficientForPriseCalculationValidator.validateCoefficientList(coefficientsFromRepository);
        return coefficientsFromRepository;
    }

    @Override
    public CoefficientForPriceCalculation update(CoefficientForPriceCalculationDto coefficientDtoForUpdate) throws IllegalArgumentException, ObjectNotFoundException {
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
    public CoefficientForPriceCalculation findOne(long idForSearch) throws IllegalArgumentException, ObjectNotFoundException {
        Optional<CoefficientForPriceCalculation> foundCoefficientOptional = priceCalculationRuleRepository.findById(idForSearch);
        CoefficientForPriceCalculation foundCoefficient = foundCoefficientOptional.orElseGet(CoefficientForPriceCalculation::new);

        CoefficientForPriseCalculationValidator.validateCoefficient(foundCoefficient);

        return foundCoefficient;
    }

    @Override
    public BigDecimal calculatePrice(ParcelParametersDto parcelParametersDto, String country) throws IllegalArgumentException {
        BigDecimal volume = BigDecimal.valueOf(getSize(parcelParametersDto));
        CoefficientForPriceCalculation foundCoefficient = priceCalculationRuleRepository.findByCountry(country);
        if (volume.doubleValue() > foundCoefficient.getParcelSizeLimit()) {
            throw new IllegalArgumentException("Parcel size cannot be more than " + foundCoefficient.getParcelSizeLimit() + " cubic meters for single delivery");
        }
        if (parcelParametersDto.getWeight() > INITIAL_WEIGHT) {
            throw new IllegalArgumentException("Parcel weight cannot be more than " + INITIAL_WEIGHT + "grams for single delivery");
        }
        return BigDecimal.valueOf(PRICE_FOR_PACKAGE + PRICE_FOR_DELIVERY + ((parcelParametersDto.getWeight() / COEFFICIENT_FOR_KILOGRAMS_CONVERT) * WEIGHT_COEFFICIENT) + (volume.doubleValue() * VOLUME_COEFFICIENT) + foundCoefficient.getCountryCoefficient() * INITIAL_COUNTRY_COEFFICIENT);
    }

    @Override
    public CoefficientForPriceCalculation findByCountry(String countryForSearch) throws IllegalArgumentException, ObjectNotFoundException {
        CoefficientForPriceCalculation foundCoefficient = priceCalculationRuleRepository.findByCountry(countryForSearch);
        CoefficientForPriseCalculationValidator.validateOnFindByCountry(foundCoefficient, countryForSearch);

        return foundCoefficient;
    }

    private double getSize(ParcelParametersDto parcelParametersDto) {
        return ((parcelParametersDto.getLength()
                * parcelParametersDto.getHeight()
                * parcelParametersDto.getWidth()) + parcelParametersDto.getWeight()) / 1000000000;
    }
}