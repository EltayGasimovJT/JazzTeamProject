package org.jazzteam.eltay.gasimov.validator;

import org.jazzteam.eltay.gasimov.entity.Voyage;

import java.util.List;

public class VoyageValidator {
    private VoyageValidator() {

    }

    public static void validateOnSave(Voyage voyageToValidate) throws IllegalArgumentException {
        if (voyageToValidate == null) {
            throw new IllegalArgumentException("Cannot save Voyage, because its null");
        }
        validateVoyage(voyageToValidate);
    }

    public static void validateVoyage(Voyage voyageToValidate) throws IllegalArgumentException {
        if (voyageToValidate == null) {
            throw new IllegalArgumentException("There is no voyage with such Id");
        }
        if (voyageToValidate.getDeparturePoint() == null) {
            throw new IllegalArgumentException("Voyage must have departure point");
        }
        if (voyageToValidate.getDestinationPoint() == null) {
            throw new IllegalArgumentException("Voyage must have destination point");
        }
    }

    public static void validateVoyageList(List<Voyage> voyagesToValidate) throws IllegalArgumentException {
        if (voyagesToValidate.isEmpty()) {
            throw new IllegalArgumentException("There is no voyages on the org.jazzteam.eltay.gasimov.repository");
        }
    }
}
