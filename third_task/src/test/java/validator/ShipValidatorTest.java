package validator;

import entity.Port;
import entity.Ship;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ShipValidatorTest {

    @Test
    void validateShipParametersNotNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> ShipValidator.validateShipParametersNotNegative(new Ship("ship",
                        -2, -52, -65, new Port())));
    }
}