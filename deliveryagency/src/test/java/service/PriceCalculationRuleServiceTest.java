package service;

import entity.Order;
import entity.OrderProcessingPoint;
import entity.ParcelParameters;
import entity.PriceCalculationRule;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.PriceCalculationRuleServiceImpl;

import java.math.BigDecimal;
import java.util.stream.Stream;

class PriceCalculationRuleServiceTest {
    private final PriceCalculationRuleService priceCalculationRuleService = new PriceCalculationRuleServiceImpl();

    private static Stream<Arguments> testDataForCalculate() {
        OrderProcessingPoint orderProcessingPoint1 = new OrderProcessingPoint();
        orderProcessingPoint1.setLocation("Russia");
        Order order1 = Order.builder()
                .id(1)
                .parcelParameters(new ParcelParameters(
                        1,
                        1,
                        1,
                        20
                ))
                .destinationPlace(orderProcessingPoint1)
                .build();

        PriceCalculationRule priceCalculationRule1 = PriceCalculationRule
                .builder()
                .id(1)
                .initialParcelPrice(40)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        orderProcessingPoint1.setLocation("Poland");
        orderProcessingPoint1.setId(2);
        Order order2 = Order.builder()
                .id(2)
                .parcelParameters(new ParcelParameters(
                        4,
                        10,
                        1,
                        20
                ))
                .destinationPlace(orderProcessingPoint1)
                .build();

        PriceCalculationRule priceCalculationRule2 = PriceCalculationRule
                .builder()
                .id(2)
                .initialParcelPrice(40)
                .countryCoefficient(1.8)
                .country("Poland")
                .parcelSizeLimit(40)
                .build();
        orderProcessingPoint1.setLocation("Ukraine");
        Order order3 = Order.builder()
                .id(3)
                .parcelParameters(new ParcelParameters(
                        4,
                        5,
                        10,
                        30
                ))
                .destinationPlace(orderProcessingPoint1)
                .build();

        PriceCalculationRule priceCalculationRule3 = PriceCalculationRule
                .builder()
                .initialParcelPrice(40)
                .countryCoefficient(1.5)
                .country("Ukraine")
                .parcelSizeLimit(60)
                .id(3)
                .build();

        return Stream.of(
                Arguments.of(order1, priceCalculationRule1, BigDecimal.valueOf(64.0)),
                Arguments.of(order2, priceCalculationRule2, BigDecimal.valueOf(108.0)),
                Arguments.of(order3, priceCalculationRule3, BigDecimal.valueOf(228.0))
        );
    }

    @Test
    void addPriceCalculationRule() {
        PriceCalculationRule priceCalculationRule = PriceCalculationRule
                .builder()
                .id(1)
                .initialParcelPrice(40)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        priceCalculationRuleService.addPriceCalculationRule(priceCalculationRule);

        Assert.assertEquals(1, priceCalculationRuleService.getRule(1).getId());
    }

    @Test
    void deletePriceCalculationRule() {
        PriceCalculationRule priceCalculationRule = PriceCalculationRule
                .builder()
                .id(1)
                .initialParcelPrice(40)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        priceCalculationRuleService.addPriceCalculationRule(priceCalculationRule);

        priceCalculationRuleService.deletePriceCalculationRule(priceCalculationRule);

        Assert.assertEquals(0, priceCalculationRuleService.findAllPriceCalculationRules().size());
    }

    @Test
    void findAllPriceCalculationRules() {
        PriceCalculationRule priceCalculationRule = PriceCalculationRule
                .builder()
                .id(1)
                .initialParcelPrice(40)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();
        priceCalculationRuleService.addPriceCalculationRule(priceCalculationRule);

        Assert.assertEquals(1, priceCalculationRuleService.findAllPriceCalculationRules().size());
    }

    @Test
    void getRule() {
        PriceCalculationRule priceCalculationRule = PriceCalculationRule
                .builder()
                .id(1)
                .initialParcelPrice(40)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();
        priceCalculationRuleService.addPriceCalculationRule(priceCalculationRule);

        PriceCalculationRule rule = priceCalculationRuleService.getRule(1);

        Assert.assertEquals(priceCalculationRule, rule);
    }

    @Test
    void update() {
        PriceCalculationRule priceCalculationRule = PriceCalculationRule
                .builder()
                .id(1)
                .initialParcelPrice(40)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();
        priceCalculationRuleService.addPriceCalculationRule(priceCalculationRule);

        priceCalculationRule.setInitialParcelPrice(52);

        PriceCalculationRule update = priceCalculationRuleService.update(priceCalculationRule);

        Assert.assertEquals(52, update.getInitialParcelPrice(), 0.001);
    }

    @ParameterizedTest
    @MethodSource("testDataForCalculate")
    void calculatePrice(Order order, PriceCalculationRule rule, BigDecimal actual) {
        BigDecimal bigDecimal = priceCalculationRuleService.calculatePrice(order, rule);
        Assert.assertEquals(actual.doubleValue(), bigDecimal.doubleValue(), 0.001);
    }
}