package org.jazzteam.eltay.gasimov.service;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.SneakyThrows;
import org.jazzteam.eltay.gasimov.dto.CoefficientForPriceCalculationDto;
import org.jazzteam.eltay.gasimov.dto.OrderDto;
import org.jazzteam.eltay.gasimov.dto.OrderProcessingPointDto;
import org.jazzteam.eltay.gasimov.dto.ParcelParametersDto;
import org.jazzteam.eltay.gasimov.entity.CoefficientForPriceCalculation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class CoefficientForPriceCalculationCalculationServiceTest {
    @Autowired
    private CoefficientForPriceCalculationService priceCalculationRuleService;
    @Autowired
    private ModelMapper modelMapper;
    public static final String ILLEGAL_ARGUMENT_EXCEPTION = "Expected IllegalArgumentException";

    private static Stream<Arguments> testDataForCalculate() {
        OrderProcessingPointDto destinationPlaceToTest = new OrderProcessingPointDto();
        destinationPlaceToTest.setLocation("Russia");
        OrderDto firstOrder = OrderDto.builder()
                .id(1L)
                .parcelParameters(
                        ParcelParametersDto.builder()
                                .height(132.0)
                                .width(132.0)
                                .length(133.0)
                                .weight(2032.0).build()
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
                                .height(432.0)
                                .width(130.0)
                                .length(211.0)
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
                                .height(424.0)
                                .width(334.0)
                                .length(130.0)
                                .weight(340.0).build())
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
                Arguments.of(firstOrder, firstCoefficientToTest, BigDecimal.valueOf(7.4462)),
                Arguments.of(secondOrder, secondCoefficientToTest, BigDecimal.valueOf(6.6011)),
                Arguments.of(thirdOrder, thirdCoefficientToTest, BigDecimal.valueOf(6.5218))
        );
    }

    private static Stream<Arguments> testNegativeData() {
        OrderProcessingPointDto destinationPlaceToTest = new OrderProcessingPointDto();
        destinationPlaceToTest.setLocation("Russia");
        OrderDto firstOrder = OrderDto.builder()
                .id(1L)
                .parcelParameters(
                        ParcelParametersDto.builder()
                                .height(132325.0)
                                .width(13232.0)
                                .length(1333.0)
                                .weight(2032.0).build()
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
                                .height(432.0)
                                .width(130.0)
                                .length(211.0)
                                .weight(224124120.0).build()
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

        return Stream.of(
                Arguments.of(firstOrder, firstCoefficientToTest),
                Arguments.of(secondOrder, secondCoefficientToTest)
        );
    }

    @ParameterizedTest
    @MethodSource("testDataForCalculate")
    void calculatePrice(OrderDto order, CoefficientForPriceCalculationDto rule, BigDecimal expected) throws ObjectNotFoundException {
        priceCalculationRuleService.save(rule);
        BigDecimal actual = priceCalculationRuleService.calculatePrice(order.getParcelParameters(), rule.getCountry());
        assertEquals(expected.doubleValue(), actual.doubleValue(), 0.001);
    }

    @Test
    void findAllPriceCalculationRules() throws ObjectNotFoundException {
        CoefficientForPriceCalculationDto firstCoefficientForPriceCalculationToTest = CoefficientForPriceCalculationDto
                .builder()
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        CoefficientForPriceCalculationDto secondCoefficientForPriceCalculationToTest = CoefficientForPriceCalculationDto
                .builder()
                .countryCoefficient(1.6)
                .country("Belarus")
                .parcelSizeLimit(50)
                .build();

        CoefficientForPriceCalculation savedFirst = priceCalculationRuleService.save(firstCoefficientForPriceCalculationToTest);
        CoefficientForPriceCalculation savedSecond = priceCalculationRuleService.save(secondCoefficientForPriceCalculationToTest);

        List<CoefficientForPriceCalculation> actual = priceCalculationRuleService.findAll();

        assertEquals(Arrays.asList(savedFirst, savedSecond), actual);
    }


    @ParameterizedTest
    @MethodSource("testNegativeData")
    void testNegativeCalculate(OrderDto order, CoefficientForPriceCalculationDto rule) throws ObjectNotFoundException {
        priceCalculationRuleService.save(rule);
        try {
            priceCalculationRuleService.calculatePrice(order.getParcelParameters(), rule.getCountry());
            Assertions.fail(ILLEGAL_ARGUMENT_EXCEPTION);
        } catch (IllegalArgumentException thrown) {
            Assertions.assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void addPriceCalculationRule() throws ObjectNotFoundException {
        CoefficientForPriceCalculationDto expectedCoefficientDto = CoefficientForPriceCalculationDto
                .builder()
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();


        CoefficientForPriceCalculation savedCoefficient = priceCalculationRuleService.save(expectedCoefficientDto);

        CoefficientForPriceCalculation actualCoefficient = priceCalculationRuleService.findOne(savedCoefficient.getId());

        assertEquals(savedCoefficient, actualCoefficient);
    }

    @SneakyThrows
    @Test
    void deletePriceCalculationRule() {
        CoefficientForPriceCalculationDto firstCoefficientForPriceCalculationToTest = CoefficientForPriceCalculationDto
                .builder()
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        CoefficientForPriceCalculationDto secondCoefficientForPriceCalculationToTest = CoefficientForPriceCalculationDto
                .builder()
                .countryCoefficient(1.6)
                .country("Belarus")
                .parcelSizeLimit(50)
                .build();

        CoefficientForPriceCalculation savedFirst = priceCalculationRuleService.save(firstCoefficientForPriceCalculationToTest);
        CoefficientForPriceCalculation savedSecond = priceCalculationRuleService.save(secondCoefficientForPriceCalculationToTest);

        priceCalculationRuleService.delete(savedFirst.getId());

        List<CoefficientForPriceCalculation> actual = priceCalculationRuleService.findAll();

        assertEquals(Collections.singletonList(savedSecond), actual);
    }

    @Test
    void getCoefficient() throws ObjectNotFoundException {
        CoefficientForPriceCalculationDto expectedCoefficientDto = CoefficientForPriceCalculationDto
                .builder()
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        CoefficientForPriceCalculation savedCoefficient = priceCalculationRuleService.save(expectedCoefficientDto);

        CoefficientForPriceCalculation actualCoefficient = priceCalculationRuleService.findOne(savedCoefficient.getId());

        assertEquals(savedCoefficient, actualCoefficient);
    }

    @Test
    void update() throws ObjectNotFoundException {
        CoefficientForPriceCalculationDto expected = CoefficientForPriceCalculationDto
                .builder()
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        CoefficientForPriceCalculation savedCoefficient = priceCalculationRuleService.save(expected);
        final int newParcelSizeLimit = 52;
        savedCoefficient.setParcelSizeLimit(newParcelSizeLimit);

        CoefficientForPriceCalculation updatedCoefficient = priceCalculationRuleService.update(modelMapper.map(savedCoefficient, CoefficientForPriceCalculationDto.class));

        CoefficientForPriceCalculationDto actual = modelMapper.map(updatedCoefficient, CoefficientForPriceCalculationDto.class);
        expected.setId(savedCoefficient.getId());
        expected.setParcelSizeLimit(newParcelSizeLimit);
        assertEquals(expected, actual);
    }
}