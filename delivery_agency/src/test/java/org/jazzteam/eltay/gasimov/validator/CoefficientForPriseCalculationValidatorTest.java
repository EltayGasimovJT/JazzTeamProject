package org.jazzteam.eltay.gasimov.validator;

import javassist.tools.rmi.ObjectNotFoundException;
import org.jazzteam.eltay.gasimov.entity.CoefficientForPriceCalculation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.jazzteam.eltay.gasimov.util.Constants.ILLEGAL_ARGUMENT_EXCEPTION;
import static org.jazzteam.eltay.gasimov.util.Constants.OBJECT_NOT_FOUND_EXCEPTION;

class CoefficientForPriseCalculationValidatorTest {
    private static Stream<Arguments> testDataForValidate() {
        CoefficientForPriceCalculation firstCoefficient = CoefficientForPriceCalculation.builder()
                .countryCoefficient(1.2)
                .country("qwr")
                .parcelSizeLimit(null)
                .build();
        CoefficientForPriceCalculation secondCoefficient = CoefficientForPriceCalculation.builder()
                .countryCoefficient(null)
                .country("qwr")
                .parcelSizeLimit(42)
                .build();
        CoefficientForPriceCalculation thirdCoefficient = CoefficientForPriceCalculation.builder()
                .countryCoefficient(1.2)
                .country(null)
                .parcelSizeLimit(42)
                .build();
        CoefficientForPriceCalculation fifthCoefficient = null;


        return Stream.of(
                Arguments.of(firstCoefficient),
                Arguments.of(secondCoefficient),
                Arguments.of(thirdCoefficient),
                Arguments.of(fifthCoefficient)
        );
    }

    @ParameterizedTest
    @MethodSource("testDataForValidate")
    void validateCoefficient(CoefficientForPriceCalculation coefficient) {
        try {
            CoefficientForPriseCalculationValidator.validateCoefficient(coefficient);
            Assertions.fail(ILLEGAL_ARGUMENT_EXCEPTION);
            Assertions.fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException | ObjectNotFoundException thrown) {
            Assertions.assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateOnSave() {
        try {
            CoefficientForPriseCalculationValidator.validateOnSave(null);
            Assertions.fail(ILLEGAL_ARGUMENT_EXCEPTION);
            Assertions.fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException | ObjectNotFoundException thrown) {
            Assertions.assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateCoefficientList() {
        try {
            CoefficientForPriseCalculationValidator.validateCoefficientList(null);
            Assertions.fail(ILLEGAL_ARGUMENT_EXCEPTION);
            Assertions.fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException | ObjectNotFoundException thrown) {
            Assertions.assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateOnFindById() {
        try {
            CoefficientForPriseCalculationValidator.validateOnFindById(null, 1L);
            Assertions.fail(ILLEGAL_ARGUMENT_EXCEPTION);
            Assertions.fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException | ObjectNotFoundException thrown) {
            Assertions.assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateOnFindByCountry() {
        try {
            CoefficientForPriseCalculationValidator.validateOnFindByCountry(null, "qwrwq");
            Assertions.fail(ILLEGAL_ARGUMENT_EXCEPTION);
            Assertions.fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException | ObjectNotFoundException thrown) {
            Assertions.assertNotEquals("", thrown.getMessage());
        }
    }
}