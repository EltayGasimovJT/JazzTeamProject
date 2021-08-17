package validator;

import entity.OrderProcessingPoint;

import java.util.List;

public class OrderProcessingPointValidator {
    private OrderProcessingPointValidator() {
    }

    public static void validateProcessingPoint(OrderProcessingPoint orderProcessingPointToValidate) throws IllegalArgumentException {
        if (orderProcessingPointToValidate == null) {
            throw new IllegalArgumentException("There is no processing point with such Id!!!");
        }
        if (orderProcessingPointToValidate.getLocation() == null){
            throw new IllegalArgumentException("Processing point must have Location!!!");
        }
        if (orderProcessingPointToValidate.getWarehouse() == null){
            throw new IllegalArgumentException("Processing point must have Warehouse connected with!!!");
        }
    }

    public static void validateOnSave(OrderProcessingPoint orderProcessingPointToValidate) throws IllegalArgumentException {
        if (orderProcessingPointToValidate == null) {
            throw new IllegalArgumentException("Processing point cannot be null!!!");
        }
        validateProcessingPoint(orderProcessingPointToValidate);
    }

    public static void validateProcessingPointList(List<OrderProcessingPoint> orderProcessingPointsToValidate) throws IllegalArgumentException {
        if (orderProcessingPointsToValidate.isEmpty()) {
            throw new IllegalArgumentException("There is no processing points on the repository!!!");
        }
    }
}
