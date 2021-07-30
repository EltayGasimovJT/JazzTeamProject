package validator;

import entity.Port;
import entity.Ship;

public class PortValidator {
    private PortValidator() {
    }

    public static void isShipsContainersMoreThanPortCapacity(Port port, Ship ship) {
        if (ship.getContainersToTake() > port.getContainersCapacity()
                || ship.getContainersToUpload() > port.getContainersCapacity()
                || ship.getContainersToUpload() + ship.getContainersToTake() > port.getContainersCapacity()) {
            throw new IllegalArgumentException("The port cannot upload or take container, because out of capacity");
        }
    }

    public static void isPortCanBeCreated(int dockQty, int containersCapacity, int currentContainersQty) {
        if (dockQty <= 0) {
            throw new IllegalArgumentException("Dock qty cannot be less or equals to zero");
        }
        if (containersCapacity <= 0) {
            throw new IllegalArgumentException("Container capacity cannot be less or equals to zero");
        }
        if (containersCapacity < currentContainersQty){
            throw new IllegalArgumentException("Current containers count cannot exceed the port capacity");
        }
    }
}
