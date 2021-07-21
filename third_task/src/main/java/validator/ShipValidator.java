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

    public static void validateIsNotOutOfCapacity(Ship ship) throws IllegalArgumentException {
        if (ship.getShipCapacity() == 0
                || ship.getContainersToUpload() > ship.getShipCapacity()
                || ship.getContainersToTake() > ship.getShipCapacity()
                || ship.getContainersToTake() + ship.getContainersToUpload() > ship.getShipCapacity()) {
            throw new IllegalArgumentException("The ship cannot upload or take container, because out of capacity");
        }
    }


}
