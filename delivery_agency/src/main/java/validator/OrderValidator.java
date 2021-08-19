package validator;

import entity.Order;

import java.util.List;

public class OrderValidator {

    private OrderValidator() {

    }

    public static void validateOrder(Order orderToValidate) throws IllegalArgumentException {
        if (orderToValidate == null) {
            throw new IllegalArgumentException("There is not Order with this Id");
        }
        if (orderToValidate.getSender() == null && orderToValidate.getRecipient() == null) {
            throw new IllegalArgumentException("The order must have recipient and sender");
        }
        if (orderToValidate.getPrice().doubleValue() < 0) {
            throw new IllegalArgumentException("Order price cannot be negative" + orderToValidate.getPrice().doubleValue());
        }
        if (orderToValidate.getDestinationPlace() == null) {
            throw new IllegalArgumentException("The order must have a destination place");
        }
        if (orderToValidate.getState() == null) {
            throw new IllegalArgumentException("Order must have state");
        }
        if (orderToValidate.getParcelParameters() == null) {
            throw new IllegalArgumentException("The order must have parameters");
        }
        if (orderToValidate.getCurrentLocation() == null) {
            throw new IllegalArgumentException("The order must have current location");
        }
    }

    public static void validateOrders(List<Order> orders) throws IllegalArgumentException {
        if (orders.isEmpty()) {
            throw new IllegalArgumentException("There is no orders on the repository");
        }
    }

    public static void validateOnSave(Order orderToValidate) throws IllegalArgumentException {
        if (orderToValidate == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        validateOrder(orderToValidate);
    }

    public static void validateOrdersOnTheWay(List<List<Order>> ordersOnTheWayToValidate) throws IllegalArgumentException {
        if(ordersOnTheWayToValidate.isEmpty()){
            throw new IllegalArgumentException("There is no orders on the way");
        }
    }
}
