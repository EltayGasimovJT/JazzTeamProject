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
        return Stream.of(Arguments.of("ship1", 0, 0, 0, new Port()),
                Arguments.of("ship2", 5, 1, 0, new Port()),
                Arguments.of("ship3", -5, 0, 0, new Port()),
                Arguments.of("ship4", -12, 20, -31, new Port()),
                Arguments.of("ship5", 1, 0, -612, new Port()));
    }

    @ParameterizedTest
    @MethodSource("wrongShipsDataToTest")
    void validateShipParametersNotNegative(String shipName, int shipCapacity,
                                           int containersToTake, int containersToUpload, Port port) {
        assertThrows(IllegalArgumentException.class,
                () -> new Ship(shipName, shipCapacity, containersToTake, containersToUpload, port));
    }
}