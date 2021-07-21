package validator;

import entity.Port;
import entity.Ship;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PortValidatorTest {
    private static Stream<Arguments> wrongPortCapacityToTest() {
        return Stream.of(
                Arguments.of(new Port(2, -125, 0)),
                Arguments.of(new Port(2, 0,-125)),
                Arguments.of(new Port(2,0,1)),
                Arguments.of(new Port(2,-1,-2))

        );
    }

    private static Stream<Arguments> wrongPortOutCapacityToTest() {
        return Stream.of(
                Arguments.of(new Port(2, 0, 1)),
                Arguments.of(new Port(2, 1,5)),
                Arguments.of(new Port(2,50,51))
        );
    }

    private static Stream<Arguments> wrongDockQtyDataToTest() {
        return Stream.of(
                Arguments.of(new Port(0, 21, 2)),
                Arguments.of(new Port(-1, 21, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("wrongPortCapacityToTest")
    void validatePortParametersNotNegative(Port port) {
        assertThrows(IllegalArgumentException.class,
                () -> PortValidator.validatePortParametersNotNegative(port));
    }

    @ParameterizedTest
    @MethodSource("wrongPortOutCapacityToTest")
    void validateOutOfCapacity(Port port) {
        assertThrows(IllegalArgumentException.class,
                () -> PortValidator.validateIsNotOutOfCapacity(port));
    }


    @ParameterizedTest
    @MethodSource("wrongDockQtyDataToTest")
    void validateDockQty(Port port) {
        assertThrows(IllegalArgumentException.class,
                () -> PortValidator.validateDockQty(port));
    }
}