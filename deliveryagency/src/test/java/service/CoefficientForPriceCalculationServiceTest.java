package service;

import entity.Order;
import entity.OrderProcessingPoint;
import entity.ParcelParameters;
import entity.CoefficientForPrice;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.CoefficientForPriceCalculationServiceImpl;

import java.math.BigDecimal;
import java.util.stream.Stream;

class CoefficientForPriceCalculationServiceTest {
    private final CoefficientForPriceCalculationService priceCalculationRuleService = new CoefficientForPriceCalculationServiceImpl();

    private static Stream<Arguments> testDataForCalculate() {
        OrderProcessingPoint orderProcessingPoint1 = new OrderProcessingPoint();
        orderProcessingPoint1.setLocation("Russia");
        Order order1 = Order.builder()
                .id(1L)
                .parcelParameters(new ParcelParameters(
                        1,
                        1,
                        1,
                        20
                ))
                .destinationPlace(orderProcessingPoint1)
                .build();

        CoefficientForPrice coefficientForPrice1 = CoefficientForPrice
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        orderProcessingPoint1.setLocation("Poland");
        orderProcessingPoint1.setId(2L);
        Order order2 = Order.builder()
                .id(2L)
                .parcelParameters(new ParcelParameters(
                        4,
                        10,
                        1,
                        20
                ))
                .destinationPlace(orderProcessingPoint1)
                .build();

        CoefficientForPrice coefficientForPrice2 = CoefficientForPrice
                .builder()
                .id(2L)
                .countryCoefficient(1.8)
                .country("Poland")
                .parcelSizeLimit(40)
                .build();
        orderProcessingPoint1.setLocation("Ukraine");
        Order order3 = Order.builder()
                .id(3L)
                .parcelParameters(new ParcelParameters(
                        4,
                        5,
                        10,
                        30
                ))
                .destinationPlace(orderProcessingPoint1)
                .build();

        CoefficientForPrice coefficientForPrice3 = CoefficientForPrice
                .builder()
                .countryCoefficient(1.5)
                .country("Ukraine")
                .parcelSizeLimit(60)
                .id(3L)
                .build();

        return Stream.of(
                Arguments.of(order1, coefficientForPrice1, BigDecimal.valueOf(64.0)),
                Arguments.of(order2, coefficientForPrice2, BigDecimal.valueOf(108.0)),
                Arguments.of(order3, coefficientForPrice3, BigDecimal.valueOf(228.0))
        );
    }

    @Test
    void addPriceCalculationRule() {
        CoefficientForPrice coefficientForPrice = CoefficientForPrice
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        priceCalculationRuleService.addPriceCalculationRule(coefficientForPrice);

        Assert.assertEquals(coefficientForPrice, priceCalculationRuleService.getRule(1L));
    }

    @Test
    void deletePriceCalculationRule() {
        CoefficientForPrice coefficientForPrice = CoefficientForPrice
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        priceCalculationRuleService.addPriceCalculationRule(coefficientForPrice);

        priceCalculationRuleService.deletePriceCalculationRule(coefficientForPrice);
        Assert.assertEquals(0, priceCalculationRuleService.findAllPriceCalculationRules().size());
    }

    @Test
    void findAllPriceCalculationRules() {
        CoefficientForPrice coefficientForPrice = CoefficientForPrice
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();
        priceCalculationRuleService.addPriceCalculationRule(coefficientForPrice);

        Assert.assertEquals(1, priceCalculationRuleService.findAllPriceCalculationRules().size());
    }

    @Test
    void getRule() {
        CoefficientForPrice coefficientForPrice = CoefficientForPrice
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();
        priceCalculationRuleService.addPriceCalculationRule(coefficientForPrice);

        CoefficientForPrice rule = priceCalculationRuleService.getRule(1);

        Assert.assertEquals(coefficientForPrice, rule);
    }

    @Test
    void update() {
        CoefficientForPrice coefficientForPrice = CoefficientForPrice
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();
        priceCalculationRuleService.addPriceCalculationRule(coefficientForPrice);

        coefficientForPrice.setParcelSizeLimit(52);

        CoefficientForPrice update = priceCalculationRuleService.update(coefficientForPrice);

        Assert.assertEquals(52, update.getParcelSizeLimit(), 0.001);
    }

    @ParameterizedTest
    @MethodSource("testDataForCalculate")
    void calculatePrice(Order order, CoefficientForPrice rule, BigDecimal actual) {
        BigDecimal bigDecimal = priceCalculationRuleService.calculatePrice(order, rule);
        Assert.assertEquals(actual.doubleValue(), bigDecimal.doubleValue(), 0.001);
    }
}