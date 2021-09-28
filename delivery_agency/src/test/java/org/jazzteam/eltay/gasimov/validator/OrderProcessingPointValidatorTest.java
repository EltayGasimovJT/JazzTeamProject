package org.jazzteam.eltay.gasimov.validator;

import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;
import org.jazzteam.eltay.gasimov.entity.Warehouse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.jazzteam.eltay.gasimov.util.Constants.ILLEGAL_ARGUMENT_EXCEPTION;
import static org.jazzteam.eltay.gasimov.util.Constants.OBJECT_NOT_FOUND_EXCEPTION;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

class OrderProcessingPointValidatorTest {
    private static Stream<Arguments> testDataForValidate() {
        OrderProcessingPoint firstProcessingPoint = new OrderProcessingPoint();
        firstProcessingPoint.setLocation(null);
        firstProcessingPoint.setWarehouse(new Warehouse());
        OrderProcessingPoint secondProcessingPoint = new OrderProcessingPoint();
        secondProcessingPoint.setLocation("qrwqw");
        secondProcessingPoint.setWarehouse(null);
        OrderProcessingPoint thirdProcessingPoint = null;

        return Stream.of(
                Arguments.of(firstProcessingPoint),
                Arguments.of(secondProcessingPoint),
                Arguments.of(thirdProcessingPoint)
        );
    }

    @ParameterizedTest
    @MethodSource("testDataForValidate")
    void validateProcessingPoint(OrderProcessingPoint point) {
        try {
            OrderProcessingPointValidator.validateProcessingPoint(point);
            fail(ILLEGAL_ARGUMENT_EXCEPTION);
            fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateOnSave() {
        try {
            OrderProcessingPointValidator.validateOnSave(null);
            fail(ILLEGAL_ARGUMENT_EXCEPTION);
            fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateProcessingPointList() {
        try {
            OrderProcessingPointValidator.validateProcessingPointList(null);
            fail(ILLEGAL_ARGUMENT_EXCEPTION);
            fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }
}