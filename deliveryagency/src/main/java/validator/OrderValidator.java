package validator;

import entity.Order;

public class OrderValidator {

    private OrderValidator() {

    }

    public static void validateOrder(Order order) throws IllegalArgumentException {
        if (order == null) {
            throw new IllegalArgumentException("The order cannot be empty " + order);
        }
        if (order.getSender() == null || order.getRecipient() == null) {
            throw new IllegalArgumentException("The order must have recipient or sender!!!");
        }
        if (order.getPrice().doubleValue() < 0) {
            throw new IllegalArgumentException("Order price cannot be negative" + order.getPrice().doubleValue());
        }
        if (order.getDestinationPlace() == null) {
            throw new IllegalArgumentException("The order must have a destination place");
        }
    }
}
