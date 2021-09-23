package org.jazzteam.eltay.gasimov.validator;

import org.jazzteam.eltay.gasimov.entity.Voyage;

import java.util.List;

public class VoyageValidator {
    private VoyageValidator() {

    }

    public static void validateOnSave(Voyage voyageToValidate) throws IllegalArgumentException {
        if (voyageToValidate == null) {
            throw new IllegalArgumentException("Введенные данные о маршруте не верны");
        }
        validateVoyage(voyageToValidate);
    }

    public static void validateVoyage(Voyage voyageToValidate) throws IllegalArgumentException {
        if (voyageToValidate == null) {
            throw new IllegalArgumentException("В базе нет данного маршрута");
        }
        if (voyageToValidate.getDeparturePoint() == null) {
            throw new IllegalArgumentException("Значение пункта отправки у маршрута должно быть заполнено");
        }
        if (voyageToValidate.getDestinationPoint() == null) {
            throw new IllegalArgumentException("Значение пункта назначения у маршрута должно быть заполнено");
        }
    }

    public static void validateVoyageList(List<Voyage> voyagesToValidate) throws IllegalArgumentException {
        if (voyagesToValidate.isEmpty()) {
            throw new IllegalArgumentException("В базе данныех нет маршрутов");
        }
    }
}
