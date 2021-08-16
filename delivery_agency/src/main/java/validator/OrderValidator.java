package validator;

import entity.Order;

import java.util.List;

public class OrderValidator {

    private OrderValidator() {

    }

    public static void validateOrder(Order order) throws IllegalArgumentException {
        if (order == null) {
            throw new IllegalArgumentException("There is not Order with this Id!!!");
        }
        if (order.getSender() == null && order.getRecipient() == null) {
            throw new IllegalArgumentException("The order must have recipient and sender!!!");
        }
        if (order.getPrice().doubleValue() < 0) {
            throw new IllegalArgumentException("Order price cannot be negative!!!" + order.getPrice().doubleValue());
        }
        if (order.getDestinationPlace() == null) {
            throw new IllegalArgumentException("The order must have a destination place!!!");
        }
        if (order.getState() == null) {
            throw new IllegalArgumentException("Order must have state!!!");
        }
        if (order.getParcelParameters() == null) {
            throw new IllegalArgumentException("The order must have parameters!!!");
        }
        if (order.getCurrentLocation() == null) {
            throw new IllegalArgumentException("The order must have current location!!!");
        }
    }

    public static void validateOrders(List<Order> orders) throws IllegalArgumentException {
        if (orders.isEmpty()) {
            throw new IllegalArgumentException("There is no orders in database!!!");
        }
    }
}
