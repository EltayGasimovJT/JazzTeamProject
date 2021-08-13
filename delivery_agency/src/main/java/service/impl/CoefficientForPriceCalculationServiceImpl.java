package service.impl;

import dto.CoefficientForPriceCalculationDto;
import entity.CoefficientForPriceCalculation;
import entity.Order;
import repository.CoefficientForPriceCalculationRepository;
import repository.impl.CoefficientForPriceCalculationRepositoryImpl;
import service.CoefficientForPriceCalculationService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.ConvertUtil.fromCoefficientForPriceCalculationToDTO;
import static util.ConvertUtil.fromDtoToCoefficientForPriceCalculation;

public class CoefficientForPriceCalculationServiceImpl implements CoefficientForPriceCalculationService {
    private final CoefficientForPriceCalculationRepository priceCalculationRuleRepository = new CoefficientForPriceCalculationRepositoryImpl();
    private static final int INITIAL_PRISE = 40;
    private static final int INITIAL_WEIGHT = 20;

    @Override
    public CoefficientForPriceCalculationDto save(CoefficientForPriceCalculationDto coefficientForPriceCalculationDto) {
        CoefficientForPriceCalculation coefficientForPriceCalculation = fromDtoToCoefficientForPriceCalculation(coefficientForPriceCalculationDto);
        return fromCoefficientForPriceCalculationToDTO(priceCalculationRuleRepository.save(coefficientForPriceCalculation));
    }

    @Override
    public void delete(Long id) {
        priceCalculationRuleRepository.delete(id);
    }

    @Override
    public List<CoefficientForPriceCalculationDto> findAll()  {
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
    public CoefficientForPriceCalculationDto findOne(long id)  {
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
    public CoefficientForPriceCalculationDto findByCountry(String country) {
        CoefficientForPriceCalculation actualCoefficient = priceCalculationRuleRepository.findByCountry(country);

        return fromCoefficientForPriceCalculationToDTO(actualCoefficient);
    }

    private double getSize(Order order) {
        return (order.getParcelParameters().getLength()
                * order.getParcelParameters().getHeight()
                * order.getParcelParameters().getWidth()) + order.getParcelParameters().getWeight();
    }
}