package validator;

import entity.Port;
import entity.Ship;

public class PortValidator {
    private PortValidator() {
    }

    public static void validatePortParametersNotNegative(Port port) throws IllegalArgumentException {
        if (port.getCurrentContainersQty() <= 0
                || port.getContainersCapacity() <= 0) {
            throw new IllegalArgumentException("Port parameters cannot be negative!!");
        }
    }

    public static void validateIsNotOutOfCapacity(Port port) throws IllegalArgumentException {
        if (port.getContainersCapacity() == 0
                || port.getCurrentContainersQty() > port.getContainersCapacity()) {
            throw new IllegalArgumentException("The port cannot upload or take container, because out of capacity");
        }
    }

    public static void validateDockQty(Port port) {
        if (port.getDockQty() <= 0) {
            throw new IllegalArgumentException("Dock qty cannot be less or equals to zero!! " + port.getDockQty());
        }
    }

    public static void isShipsContainersMoreThanPortCapacity(Port port, Ship ship) {
        if (ship.getContainersToTake() > port.getContainersCapacity()
                || ship.getContainersToUpload() > port.getContainersCapacity()
                || ship.getContainersToUpload() + ship.getContainersToTake()> port.getContainersCapacity()){
            throw new IllegalArgumentException("The port cannot upload or take container, because out of capacity");
        }
    }
}
