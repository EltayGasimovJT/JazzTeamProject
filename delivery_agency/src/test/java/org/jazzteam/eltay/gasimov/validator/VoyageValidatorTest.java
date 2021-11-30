package org.jazzteam.eltay.gasimov.validator;

import org.jazzteam.eltay.gasimov.entity.Voyage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.jazzteam.eltay.gasimov.util.Constants.ILLEGAL_ARGUMENT_EXCEPTION;
import static org.jazzteam.eltay.gasimov.util.Constants.OBJECT_NOT_FOUND_EXCEPTION;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

class VoyageValidatorTest {
    private static Stream<Arguments> testDataForValidate() {
        Voyage firstVoyage = new Voyage();
        firstVoyage.setDestinationPoint("wqwew");
        Voyage secondVoyage = new Voyage();
        secondVoyage.setDeparturePoint("qwfasfas");
        Voyage thirdVoyage = null;

        return Stream.of(
                Arguments.of(firstVoyage),
                Arguments.of(secondVoyage),
                Arguments.of(thirdVoyage)
        );
    }

    @ParameterizedTest
    @MethodSource("testDataForValidate")
    void validateOnSave(Voyage voyage) {
        try {
            VoyageValidator.validateVoyage(voyage);
            fail(ILLEGAL_ARGUMENT_EXCEPTION);
            fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateVoyage() {
        try {
            VoyageValidator.validateOnSave(null);
            fail(ILLEGAL_ARGUMENT_EXCEPTION);
            fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateVoyageList() {
        try {
            VoyageValidator.validateVoyageList(null);
            fail(ILLEGAL_ARGUMENT_EXCEPTION);
            fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }
}