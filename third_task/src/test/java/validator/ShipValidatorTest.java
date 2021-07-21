package validator;

import entity.Port;
import entity.Ship;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ShipValidatorTest {
    private static Stream<Arguments> wrongShipsDataToTest() {
        return Stream.of(
                Arguments.of(new Ship("ship",
                        -2, 52, 65, new Port())),
                Arguments.of(new Ship("ship",
                        2, 52, -65, new Port())),
                Arguments.of(new Ship("ship",
                        2, -52, 65, new Port())),
                Arguments.of(new Ship("ship",
                        -2, -52, -65, new Port())),
                Arguments.of(new Ship("ship",
                        0, 0, 0, new Port())));
    }

    private static Stream<Arguments> wrongShipCapacityDataToTest() {
        return Stream.of(
                Arguments.of(new Ship("ship",
                        0, 70, 50, new Port())),
                Arguments.of(new Ship("ship",
                        80, 0, 50, new Port())),
                Arguments.of(new Ship("ship",
                        76, 25, 50, new Port())),
                Arguments.of(new Ship("ship",
                        2, 0, 0, new Port()))
        );
    }

    @ParameterizedTest
    @MethodSource("wrongShipsDataToTest")
    void validateShipParametersNotNegative(Ship ship) {
        assertThrows(IllegalArgumentException.class,
                () -> ShipValidator.validateShipParametersNotNegative(ship));
    }

    @ParameterizedTest
    @MethodSource("wrongShipCapacityDataToTest")
    void testValidateShipParametersNotNegative(Ship ship) {
        assertThrows(IllegalArgumentException.class,
                () -> ShipValidator.validateIsNotOutOfCapacity(ship));
    }
}