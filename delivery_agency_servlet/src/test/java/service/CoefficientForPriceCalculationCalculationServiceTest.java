package service;

import entity.CoefficientForPriceCalculation;
import entity.Order;
import entity.OrderProcessingPoint;
import entity.ParcelParameters;
import lombok.SneakyThrows;
import org.junit.Assert;
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
        Order order1 = Order.builder()
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

        CoefficientForPriceCalculation firstCoefficient = CoefficientForPriceCalculation
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        orderProcessingPoint.setLocation("Poland");
        orderProcessingPoint.setId(2L);
        Order order2 = Order.builder()
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

        CoefficientForPriceCalculation secondCoefficient = CoefficientForPriceCalculation
                .builder()
                .id(2L)
                .countryCoefficient(1.8)
                .country("Poland")
                .parcelSizeLimit(40)
                .build();
        orderProcessingPoint.setLocation("Ukraine");
        Order order3 = Order.builder()
                .id(3L)
                .parcelParameters(
                        ParcelParameters.builder()
                                .height(4)
                                .width(5)
                                .length(10)
                                .weight(30).build())
                .destinationPlace(orderProcessingPoint)
                .build();

        CoefficientForPriceCalculation thirdCoefficient = CoefficientForPriceCalculation
                .builder()
                .countryCoefficient(1.5)
                .country("Ukraine")
                .parcelSizeLimit(60)
                .id(3L)
                .build();

        return Stream.of(
                Arguments.of(order1, firstCoefficient, BigDecimal.valueOf(64.0)),
                Arguments.of(order2, secondCoefficient, BigDecimal.valueOf(108.0)),
                Arguments.of(order3, thirdCoefficient, BigDecimal.valueOf(342.0))
        );
    }

    @Test
    void addPriceCalculationRule() throws SQLException {
        CoefficientForPriceCalculation coefficientForPriceCalculation = CoefficientForPriceCalculation
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        priceCalculationRuleService.addPriceCalculationRule(coefficientForPriceCalculation);

        Assert.assertEquals(coefficientForPriceCalculation, priceCalculationRuleService.getCoefficient(1L));
    }

    @SneakyThrows
    @Test
    void deletePriceCalculationRule() {
        CoefficientForPriceCalculation coefficientForPriceCalculation = CoefficientForPriceCalculation
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        priceCalculationRuleService.addPriceCalculationRule(coefficientForPriceCalculation);

        priceCalculationRuleService.deletePriceCalculationRule(coefficientForPriceCalculation);
        Assert.assertEquals(0, priceCalculationRuleService.findAllPriceCalculationRules().size());
    }

    @Test
    void findAllPriceCalculationRules() throws SQLException {
        CoefficientForPriceCalculation coefficientForPriceCalculation = CoefficientForPriceCalculation
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();
        priceCalculationRuleService.addPriceCalculationRule(coefficientForPriceCalculation);

        Assert.assertEquals(1, priceCalculationRuleService.findAllPriceCalculationRules().size());
    }

    @Test
    void getCoefficient() throws SQLException {
        CoefficientForPriceCalculation coefficientForPriceCalculation = CoefficientForPriceCalculation
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();
        priceCalculationRuleService.addPriceCalculationRule(coefficientForPriceCalculation);

        CoefficientForPriceCalculation rule = priceCalculationRuleService.getCoefficient(1);

        Assert.assertEquals(coefficientForPriceCalculation, rule);
    }

    @Test
    void update() throws SQLException {
        CoefficientForPriceCalculation coefficientForPriceCalculation = CoefficientForPriceCalculation
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();
        priceCalculationRuleService.addPriceCalculationRule(coefficientForPriceCalculation);

        coefficientForPriceCalculation.setParcelSizeLimit(52);

        CoefficientForPriceCalculation update = priceCalculationRuleService.update(coefficientForPriceCalculation);

        Assert.assertEquals(52, update.getParcelSizeLimit(), 0.001);
    }

    @ParameterizedTest
    @MethodSource("testDataForCalculate")
    void calculatePrice(Order order, CoefficientForPriceCalculation rule, BigDecimal actual) {
        BigDecimal bigDecimal = priceCalculationRuleService.calculatePrice(order, rule);
        Assert.assertEquals(actual.doubleValue(), bigDecimal.doubleValue(), 0.001);
    }
}