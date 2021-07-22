package validator;

import entity.Port;
import entity.Ship;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PortValidatorTest {
    private static Stream<Arguments> testWrongDataForPortCreation() {
        return Stream.of(
                Arguments.of(-5, 21, 0),
                Arguments.of(1, 21, 22),
                Arguments.of(0, 0, 0),
                Arguments.of(0, -22, 0),
                Arguments.of(1, 0, -41)
        );
    }

    @ParameterizedTest
    @MethodSource("testWrongDataForPortCreation")
    void isPortCanBeCreated(int dockQty, int capacity, int currentContainers) {
        assertThrows(IllegalArgumentException.class,
                () -> new Port(dockQty, capacity, currentContainers));
    }
}