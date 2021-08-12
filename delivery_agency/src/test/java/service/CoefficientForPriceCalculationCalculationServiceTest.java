package service;

import dto.CoefficientForPriceCalculationDto;
import entity.CoefficientForPriceCalculation;
import entity.Order;
import entity.OrderProcessingPoint;
import entity.ParcelParameters;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.CoefficientForPriceCalculationServiceImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.stream.Stream;

class CoefficientForPriceCalculationCalculationServiceTest {
    private final CoefficientForPriceCalculationService priceCalculationRuleService = new CoefficientForPriceCalculationServiceImpl();

    private static Stream<Arguments> testDataForCalculate() {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setLocation("Russia");
        Order firstOrder = Order.builder()
                .id(1L)
                .parcelParameters(
                        ParcelParameters.builder()
                                .height(1)
                                .width(1)
                                .length(1)
                                .weight(20).build()
                )
                .destinationPlace(orderProcessingPoint)
                .build();

        CoefficientForPriceCalculationDto firstCoefficient = CoefficientForPriceCalculationDto
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        orderProcessingPoint.setLocation("Poland");
        orderProcessingPoint.setId(2L);
        Order secondOrder = Order.builder()
                .id(2L)
                .parcelParameters(
                        ParcelParameters.builder()
                                .height(4)
                                .width(10)
                                .length(1)
                                .weight(20).build()
                )
                .destinationPlace(orderProcessingPoint)
                .build();

        CoefficientForPriceCalculationDto secondCoefficient = CoefficientForPriceCalculationDto
                .builder()
                .id(2L)
                .countryCoefficient(1.8)
                .country("Poland")
                .parcelSizeLimit(40)
                .build();
        orderProcessingPoint.setLocation("Ukraine");
        Order thirdOrder = Order.builder()
                .id(3L)
                .parcelParameters(
                        ParcelParameters.builder()
                                .height(4)
                                .width(5)
                                .length(10)
                                .weight(30).build())
                .destinationPlace(orderProcessingPoint)
                .build();

        CoefficientForPriceCalculationDto thirdCoefficient = CoefficientForPriceCalculationDto
                .builder()
                .countryCoefficient(1.5)
                .country("Ukraine")
                .parcelSizeLimit(60)
                .id(3L)
                .build();

        return Stream.of(
                Arguments.of(firstOrder, firstCoefficient, BigDecimal.valueOf(64.0)),
                Arguments.of(secondOrder, secondCoefficient, BigDecimal.valueOf(108.0)),
                Arguments.of(thirdOrder, thirdCoefficient, BigDecimal.valueOf(342.0))
        );
    }

    @Test
    void addPriceCalculationRule() throws SQLException {
        CoefficientForPriceCalculationDto expectedCoefficient = CoefficientForPriceCalculationDto
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        priceCalculationRuleService.addPriceCalculationRule(expectedCoefficient);

        CoefficientForPriceCalculationDto actualCoefficient = priceCalculationRuleService.getCoefficient(1L);
        Assertions.assertEquals(expectedCoefficient, actualCoefficient);
    }

    @SneakyThrows
    @Test
    void deletePriceCalculationRule() {
        CoefficientForPriceCalculationDto coefficientForPriceCalculation = CoefficientForPriceCalculationDto
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        priceCalculationRuleService.addPriceCalculationRule(coefficientForPriceCalculation);

        priceCalculationRuleService.deletePriceCalculationRule(coefficientForPriceCalculation);

        int expectedSize = 0;

        int actualSize = priceCalculationRuleService.findAllPriceCalculationRules().size();
        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    void findAllPriceCalculationRules() throws SQLException {
        CoefficientForPriceCalculationDto coefficientForPriceCalculation = CoefficientForPriceCalculationDto
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();
        priceCalculationRuleService.addPriceCalculationRule(coefficientForPriceCalculation);

        int expectedSize = 1;

        int actualSize = priceCalculationRuleService.findAllPriceCalculationRules().size();

        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    void getCoefficient() throws SQLException {
        CoefficientForPriceCalculationDto expectedCoefficient = CoefficientForPriceCalculationDto
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();
        priceCalculationRuleService.addPriceCalculationRule(expectedCoefficient);

        CoefficientForPriceCalculationDto actualCoefficient = priceCalculationRuleService.getCoefficient(1);

        Assertions.assertEquals(expectedCoefficient, actualCoefficient);
    }

    @Test
    void update() throws SQLException {
        CoefficientForPriceCalculationDto coefficientForPriceCalculation = CoefficientForPriceCalculationDto
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();
        priceCalculationRuleService.addPriceCalculationRule(coefficientForPriceCalculation);

        coefficientForPriceCalculation.setParcelSizeLimit(52);

        CoefficientForPriceCalculationDto update = priceCalculationRuleService.update(coefficientForPriceCalculation);

        int expectedParcelSizeLimit = 52;

        int actualParcelSizeLimit = update.getParcelSizeLimit();

        Assertions.assertEquals(expectedParcelSizeLimit, actualParcelSizeLimit, 0.001);
    }

    @ParameterizedTest
    @MethodSource("testDataForCalculate")
    void calculatePrice(Order order, CoefficientForPriceCalculationDto rule, BigDecimal expected) {
        BigDecimal actual = priceCalculationRuleService.calculatePrice(order, rule);
        Assertions.assertEquals(expected.doubleValue(), actual.doubleValue(), 0.001);
    }
}