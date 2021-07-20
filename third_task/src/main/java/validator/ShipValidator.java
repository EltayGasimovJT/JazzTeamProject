package validator;


import entity.Ship;

public class ShipValidator {

    private ShipValidator() {
    }

    public static void validateShipParametersNotNegative(Ship ship) throws IllegalArgumentException {
        if (ship.getContainersToTake() <= 0
                || ship.getContainersToUpload() <= 0
                || ship.getShipCapacity() <= 0) {
            throw new IllegalArgumentException("Ship parameters cannot be negative!!");
        }
    }
}
