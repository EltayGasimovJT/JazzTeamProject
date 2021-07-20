package validator;

import entity.Port;
import entity.Ship;
import org.junit.Test;

class ShipValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testValidateShipParametersNotNegative() {
        ShipValidator.validateShipParametersNotNegative(new Ship("ship",
                -12, -52, -65, new Port()));
    }
}