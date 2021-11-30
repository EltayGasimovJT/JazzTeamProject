package org.jazzteam.eltay.gasimov.validator;

import org.jazzteam.eltay.gasimov.entity.Warehouse;

import java.util.List;

public class WarehouseValidator {
    private WarehouseValidator(){

    }

    public static void validateOnSave(Warehouse warehouseToValidate) throws IllegalArgumentException {
        if (warehouseToValidate == null) {
            throw new IllegalArgumentException("Введенные данные о промежуточном складе не верны");
        }
        validateWarehouse(warehouseToValidate);
    }

    public static void validateWarehouse(Warehouse warehouseToValidate) throws IllegalArgumentException {
        if (warehouseToValidate == null) {
            throw new IllegalArgumentException("В базе нет промежуточного склада с таким id: ");
        }
        if (warehouseToValidate.getLocation() == null) {
            throw new IllegalArgumentException("Значение местоположения промежуточного склада должно быть заполнено");
        }
    }

    public static void validateWarehouseList(List<Warehouse> warehousesToValidate) throws IllegalArgumentException {
        if (warehousesToValidate == null) {
            throw new IllegalArgumentException("В базе данных нет промежуточных складов");
        }
    }
}
