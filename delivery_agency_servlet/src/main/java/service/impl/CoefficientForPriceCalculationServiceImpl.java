package service.impl;

import dto.CoefficientForPriceCalculationDto;
import entity.CoefficientForPriceCalculation;
import entity.Order;
import repository.CoefficientForPriceCalculationRepository;
import repository.impl.CoefficientForPriceCalculationRepositoryImpl;
import service.CoefficientForPriceCalculationService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoefficientForPriceCalculationServiceImpl implements CoefficientForPriceCalculationService {
    private final CoefficientForPriceCalculationRepository priceCalculationRuleRepository = new CoefficientForPriceCalculationRepositoryImpl();
    private static final int INITIAL_PRISE = 40;
    private static final int INITIAL_WEIGHT = 20;

    @Override
    public CoefficientForPriceCalculationDto addPriceCalculationRule(CoefficientForPriceCalculationDto coefficientForPriceCalculationDto) throws SQLException {
        CoefficientForPriceCalculation coefficientForPriceCalculation = fromDtoToCoefficientForPriceCalculation(coefficientForPriceCalculationDto);
        return fromCoefficientForPriceCalculationToDTO(priceCalculationRuleRepository.save(coefficientForPriceCalculation));
    }

    @Override
    public void deletePriceCalculationRule(CoefficientForPriceCalculationDto coefficientForPriceCalculationDto) throws SQLException {
        priceCalculationRuleRepository.delete(fromDtoToCoefficientForPriceCalculation(coefficientForPriceCalculationDto).getId());
    }

    @Override
    public List<CoefficientForPriceCalculationDto> findAllPriceCalculationRules() throws SQLException {
        List<CoefficientForPriceCalculation> coefficientsFromRepository = priceCalculationRuleRepository.findAll();
        List<CoefficientForPriceCalculationDto> coefficientForPriceCalculationDtos = new ArrayList<>();
        for (CoefficientForPriceCalculation coefficientForPriceCalculation : coefficientsFromRepository) {
            coefficientForPriceCalculationDtos.add(fromCoefficientForPriceCalculationToDTO(coefficientForPriceCalculation));
        }
        return coefficientForPriceCalculationDtos;
    }

    @Override
    public CoefficientForPriceCalculationDto update(CoefficientForPriceCalculationDto coefficientForPriceCalculationDto) throws SQLException {
        CoefficientForPriceCalculation updatedCoefficient = priceCalculationRuleRepository.update(fromDtoToCoefficientForPriceCalculation(coefficientForPriceCalculationDto));
        return fromCoefficientForPriceCalculationToDTO(updatedCoefficient);
    }

    @Override
    public CoefficientForPriceCalculationDto getCoefficient(long id) throws SQLException {
        return fromCoefficientForPriceCalculationToDTO(priceCalculationRuleRepository.findOne(id));
    }

    @Override
    public BigDecimal calculatePrice(Order order, CoefficientForPriceCalculationDto coefficientForPriceCalculationDto) throws IllegalArgumentException {
        BigDecimal resultPrice = new BigDecimal(1);
        BigDecimal size = BigDecimal.valueOf(getSize(order));
        BigDecimal parcelSizeLimit = BigDecimal.valueOf(coefficientForPriceCalculationDto.getParcelSizeLimit());
        if (size.doubleValue() > parcelSizeLimit.doubleValue() && order.getParcelParameters().getWeight() > INITIAL_WEIGHT) {
            resultPrice =
                    resultPrice
                            .multiply(BigDecimal.valueOf(coefficientForPriceCalculationDto.getCountryCoefficient())
                                    .multiply(BigDecimal.valueOf(INITIAL_PRISE)
                                            .multiply(BigDecimal.valueOf(order.getParcelParameters().getWeight() / INITIAL_WEIGHT))
                                            .multiply((size.divide(parcelSizeLimit, 1))))
                            );
        } else if (size.doubleValue() > parcelSizeLimit.doubleValue()) {
            resultPrice =
                    resultPrice
                            .multiply(BigDecimal.valueOf(coefficientForPriceCalculationDto.getCountryCoefficient())
                                    .multiply(BigDecimal.valueOf(INITIAL_PRISE)
                                            .multiply((size.divide(parcelSizeLimit, 1)))));
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
    public CoefficientForPriceCalculationDto findByCountry(String country) {
        CoefficientForPriceCalculation actualCoefficient = priceCalculationRuleRepository.findByCountry(country);

        CoefficientForPriceCalculationDto coefficientForPriceCalculationDto = fromCoefficientForPriceCalculationToDTO(actualCoefficient);
        return coefficientForPriceCalculationDto;
    }

    private double getSize(Order order) {
        return (order.getParcelParameters().getLength()
                * order.getParcelParameters().getHeight()
                * order.getParcelParameters().getWidth()) + order.getParcelParameters().getWeight();
    }

    private CoefficientForPriceCalculationDto fromCoefficientForPriceCalculationToDTO(CoefficientForPriceCalculation coefficientForPriceCalculation) {
        return CoefficientForPriceCalculationDto.builder()
                .id(coefficientForPriceCalculation.getId())
                .country(coefficientForPriceCalculation.getCountry())
                .countryCoefficient(coefficientForPriceCalculation.getCountryCoefficient())
                .parcelSizeLimit(coefficientForPriceCalculation.getParcelSizeLimit())
                .build();
    }

    private CoefficientForPriceCalculation fromDtoToCoefficientForPriceCalculation(CoefficientForPriceCalculationDto coefficientForPriceCalculationDto) {
        return CoefficientForPriceCalculation.builder()
                .id(coefficientForPriceCalculationDto.getId())
                .country(coefficientForPriceCalculationDto.getCountry())
                .countryCoefficient(coefficientForPriceCalculationDto.getCountryCoefficient())
                .parcelSizeLimit(coefficientForPriceCalculationDto.getParcelSizeLimit())
                .build();
    }
}
