package org.jazzteam.eltay.gasimov.validator;

import org.jazzteam.eltay.gasimov.entity.Order;

import java.util.List;

public class OrderValidator {
    private OrderValidator() {

    }

    public static void validateOrder(Order orderToValidate) throws IllegalArgumentException {
        if (orderToValidate == null) {
            throw new IllegalArgumentException("Не удалось найти заказ, так как его не существует в базе");
        }
        if (orderToValidate.getSender() == null && orderToValidate.getRecipient() == null) {
            throw new IllegalArgumentException("Значение данных об отправителе должно быть заполнено");
        }
        if (orderToValidate.getPrice().doubleValue() < 0) {
            throw new IllegalArgumentException("Значение стоимости заказа не может быть меньше нуля: " + orderToValidate.getPrice().doubleValue());
        }
        if (orderToValidate.getDestinationPlace() == null) {
            throw new IllegalArgumentException("Значение данных о метсте назначения должно быть заполнено");
        }
        if (orderToValidate.getState() == null) {
            throw new IllegalArgumentException("Значение данных о состоянии заказа должно быть заполнено");
        }
        if (orderToValidate.getParcelParameters() == null) {
            throw new IllegalArgumentException("Значение данных о габаритах посылки должно быть заполнено");
        }
        if (orderToValidate.getCurrentLocation() == null) {
            throw new IllegalArgumentException("Значение данных отекущем местоположении заказа должно быть заполнено");
        }
    }

    public static void validateOrders(List<Order> orders) throws IllegalArgumentException {
        if (orders == null) {
            throw new IllegalArgumentException("В базе данных нет заказов");
        }
    }

    public static void validateOnSave(Order orderToValidate) throws IllegalArgumentException {
        if (orderToValidate == null) {
            throw new IllegalArgumentException("Введенные данные заказа не верны");
        }
        validateOrder(orderToValidate);
    }

    public static void validateOrdersOnTheWay(List<List<Order>> ordersOnTheWayToValidate) throws IllegalArgumentException {
        if(ordersOnTheWayToValidate == null){
            throw new IllegalArgumentException("На данный момент нет заказов в пути");
        }
    }
}
