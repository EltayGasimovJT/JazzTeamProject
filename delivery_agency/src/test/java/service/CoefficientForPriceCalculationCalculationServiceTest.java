package service;

import dto.CoefficientForPriceCalculationDto;
import dto.OrderDto;
import dto.OrderProcessingPointDto;
import dto.ParcelParametersDto;
import entity.CoefficientForPriceCalculation;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.modelmapper.ModelMapper;
import service.impl.CoefficientForPriceCalculationServiceImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.stream.Stream;

class CoefficientForPriceCalculationCalculationServiceTest {
    private final CoefficientForPriceCalculationService priceCalculationRuleService = new CoefficientForPriceCalculationServiceImpl();

    private final ModelMapper modelMapper = new ModelMapper();

    private static Stream<Arguments> testDataForCalculate() {
        OrderProcessingPointDto destinationPlaceToTest = new OrderProcessingPointDto();
        destinationPlaceToTest.setLocation("Russia");
        OrderDto firstOrder = OrderDto.builder()
                .id(1L)
                .parcelParameters(
                        ParcelParametersDto.builder()
                                .height(1.0)
                                .width(1.0)
                                .length(1.0)
                                .weight(20.0).build()
                )
                .destinationPlace(destinationPlaceToTest)
                .build();

        CoefficientForPriceCalculationDto firstCoefficientToTest = CoefficientForPriceCalculationDto
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        destinationPlaceToTest.setLocation("Poland");
        destinationPlaceToTest.setId(2L);
        OrderDto secondOrder = OrderDto.builder()
                .id(2L)
                .parcelParameters(
                        ParcelParametersDto.builder()
                                .height(4.0)
                                .width(10.0)
                                .length(1.0)
                                .weight(20.0).build()
                )
                .destinationPlace(destinationPlaceToTest)
                .build();

        CoefficientForPriceCalculationDto secondCoefficientToTest = CoefficientForPriceCalculationDto
                .builder()
                .id(2L)
                .countryCoefficient(1.8)
                .country("Poland")
                .parcelSizeLimit(40)
                .build();
        destinationPlaceToTest.setLocation("Ukraine");
        OrderDto thirdOrder = OrderDto.builder()
                .id(3L)
                .parcelParameters(
                        ParcelParametersDto.builder()
                                .height(4.0)
                                .width(5.0)
                                .length(10.0)
                                .weight(30.0).build())
                .destinationPlace(destinationPlaceToTest)
                .build();

        CoefficientForPriceCalculationDto thirdCoefficientToTest = CoefficientForPriceCalculationDto
                .builder()
                .countryCoefficient(1.5)
                .country("Ukraine")
                .parcelSizeLimit(60)
                .id(3L)
                .build();

        return Stream.of(
                Arguments.of(firstOrder, firstCoefficientToTest, BigDecimal.valueOf(64.0)),
                Arguments.of(secondOrder, secondCoefficientToTest, BigDecimal.valueOf(108.0)),
                Arguments.of(thirdOrder, thirdCoefficientToTest, BigDecimal.valueOf(342.0))
        );
    }

    @Test
    void addPriceCalculationRule() throws SQLException {
        CoefficientForPriceCalculationDto expectedCoefficientDto = CoefficientForPriceCalculationDto
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        priceCalculationRuleService.save(expectedCoefficientDto);

        CoefficientForPriceCalculation actualCoefficient = priceCalculationRuleService.findOne(1L);

        CoefficientForPriceCalculationDto actualCoefficientDto = modelMapper.map(actualCoefficient, CoefficientForPriceCalculationDto.class);

        Assertions.assertEquals(expectedCoefficientDto, actualCoefficientDto);
    }

    @SneakyThrows
    @Test
    void deletePriceCalculationRule() {
        CoefficientForPriceCalculationDto firstCoefficientForPriceCalculationToTest = CoefficientForPriceCalculationDto
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        CoefficientForPriceCalculationDto secondCoefficientForPriceCalculationToTest = CoefficientForPriceCalculationDto
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Belarus")
                .parcelSizeLimit(50)
                .build();

        priceCalculationRuleService.save(firstCoefficientForPriceCalculationToTest);
        priceCalculationRuleService.save(secondCoefficientForPriceCalculationToTest);

        priceCalculationRuleService.delete(firstCoefficientForPriceCalculationToTest.getId());

        int expectedSize = 1;

        int actualSize = priceCalculationRuleService.findAll().size();

        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    void findAllPriceCalculationRules() throws SQLException {
        CoefficientForPriceCalculationDto coefficientForPriceCalculationToTest = CoefficientForPriceCalculationDto
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();
        priceCalculationRuleService.save(coefficientForPriceCalculationToTest);

        int expectedSize = 1;

        int actualSize = priceCalculationRuleService.findAll().size();

        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    void getCoefficient() throws SQLException {
        CoefficientForPriceCalculationDto expectedCoefficientDto = CoefficientForPriceCalculationDto
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();
        priceCalculationRuleService.save(expectedCoefficientDto);

        CoefficientForPriceCalculation actualCoefficient = priceCalculationRuleService.findOne(1);

        CoefficientForPriceCalculationDto actualCoefficientDto = modelMapper.map(actualCoefficient, CoefficientForPriceCalculationDto.class);

        Assertions.assertEquals(expectedCoefficientDto, actualCoefficientDto);
    }

    @Test
    void update() throws SQLException {
        CoefficientForPriceCalculationDto coefficientForPriceCalculationToTest = CoefficientForPriceCalculationDto
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();
        priceCalculationRuleService.save(coefficientForPriceCalculationToTest);

        coefficientForPriceCalculationToTest.setParcelSizeLimit(52);

        CoefficientForPriceCalculation updatedCoefficient = priceCalculationRuleService.update(coefficientForPriceCalculationToTest);

        CoefficientForPriceCalculationDto actualCoefficientDto = modelMapper.map(updatedCoefficient, CoefficientForPriceCalculationDto.class);

        int expectedParcelSizeLimit = 52;

        int actualParcelSizeLimit = actualCoefficientDto.getParcelSizeLimit();

        Assertions.assertEquals(expectedParcelSizeLimit, actualParcelSizeLimit, 0.001);
    }

    @ParameterizedTest
    @MethodSource("testDataForCalculate")
    void calculatePrice(OrderDto order, CoefficientForPriceCalculationDto rule, BigDecimal expected) {
        BigDecimal actual = priceCalculationRuleService.calculatePrice(order, rule);
        Assertions.assertEquals(expected.doubleValue(), actual.doubleValue(), 0.001);
    }

}