package validator;

import entity.Warehouse;

import java.util.List;

public class WarehouseValidator {
    private WarehouseValidator(){

    }

    public static void validateOnSave(Warehouse warehouseToValidate) throws IllegalArgumentException {
        if (warehouseToValidate == null) {
            throw new IllegalArgumentException("Cannot save warehouse because its null");
        }
        validateWarehouse(warehouseToValidate);
    }

    public static void validateWarehouse(Warehouse warehouseToValidate) throws IllegalArgumentException {
        if (warehouseToValidate == null) {
            throw new IllegalArgumentException("There is now warehouse with such id");
        }
        if (warehouseToValidate.getLocation() == null) {
            throw new IllegalArgumentException("Warehouse must have location");
        }
    }

    public static void validateWarehouseList(List<Warehouse> warehousesToValidate) throws IllegalArgumentException {
        if (warehousesToValidate.isEmpty()) {
            throw new IllegalArgumentException("There is no warehouses in repository");
        }
    }
}
