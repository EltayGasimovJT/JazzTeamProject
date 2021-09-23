package org.jazzteam.eltay.gasimov.validator;

import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;

import java.util.List;

public class OrderProcessingPointValidator {
    private OrderProcessingPointValidator() {
    }

    public static void validateProcessingPoint(OrderProcessingPoint orderProcessingPointToValidate) throws IllegalArgumentException {
        if (orderProcessingPointToValidate == null) {
            throw new IllegalArgumentException("В базе данных нет такого пункта отправки/выдачи");
        }
        if (orderProcessingPointToValidate.getLocation() == null){
            throw new IllegalArgumentException("Значение места положения пункта отправки/выдачи должно быть заполнено");
        }
        if (orderProcessingPointToValidate.getWarehouse() == null){
            throw new IllegalArgumentException("Значение зарегистрированного промежуточного склада связанным с пунктом отправки/выдачи должно быть заполнено");
        }
    }

    public static void validateOnSave(OrderProcessingPoint orderProcessingPointToValidate) throws IllegalArgumentException {
        if (orderProcessingPointToValidate == null) {
            throw new IllegalArgumentException("Введенные данные пункта отправки/выдачи не верны");
        }
        validateProcessingPoint(orderProcessingPointToValidate);
    }

    public static void validateProcessingPointList(List<OrderProcessingPoint> orderProcessingPointsToValidate) throws IllegalArgumentException {
        if (orderProcessingPointsToValidate.isEmpty()) {
            throw new IllegalArgumentException("В базе данных нет пунктов отправки/выдачи");
        }
    }
}
