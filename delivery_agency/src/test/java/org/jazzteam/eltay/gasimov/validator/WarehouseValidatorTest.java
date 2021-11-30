package org.jazzteam.eltay.gasimov.validator;

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

class WarehouseValidatorTest {
    private static Stream<Arguments> testDataForValidate() {
        Warehouse firstWarehouse = new Warehouse();
        Warehouse secondWarehouse = null;

        return Stream.of(
                Arguments.of(firstWarehouse),
                Arguments.of(secondWarehouse)
        );
    }

    @ParameterizedTest
    @MethodSource("testDataForValidate")
    void validateWarehouse(Warehouse warehouse) {
        try {
            WarehouseValidator.validateWarehouse(warehouse);
            fail(ILLEGAL_ARGUMENT_EXCEPTION);
            fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateOnSave() {
        try {
            WarehouseValidator.validateOnSave(null);
            fail(ILLEGAL_ARGUMENT_EXCEPTION);
            fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    void validateWarehouseList() {
        try {
            WarehouseValidator.validateWarehouseList(null);
            fail(ILLEGAL_ARGUMENT_EXCEPTION);
            fail(OBJECT_NOT_FOUND_EXCEPTION);
        } catch (IllegalArgumentException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }
}