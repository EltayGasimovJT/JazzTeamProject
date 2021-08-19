package org.jazzteam.eltay.gasimov.validator;


public class ShipValidator {

    private ShipValidator() {
    }

    public static void isShipCanBeCreated(int capacity, int containersToTake, int containersToUpload) {
        if (capacity < containersToTake
                || capacity < containersToUpload
                || capacity < containersToTake + containersToUpload
                || capacity <= 0
                || containersToTake <= 0
                || containersToUpload <= 0) {
            throw new IllegalArgumentException("Invalid ship parameters");
        }
    }
}
