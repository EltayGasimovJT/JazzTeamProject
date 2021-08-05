package repository.impl;

import entity.Client;
import entity.Order;
import repository.OrderRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    private final List<Order> orders = new ArrayList<>();
    private final List<List<Order>> ordersOnTheWay = new ArrayList<>();

    @Override
    public Order findByRecipient(Client recipient) {
        return orders.stream()
                .filter(order1 -> order1.getRecipient().getId() == recipient.getId())
                .findFirst()
                .orElse(null);
    }

    @Override
    public Order findBySender(Client sender) {
        return null;
    }

    @Override
    public List<Order> saveSentOrders(List<Order> orders) {
        this.ordersOnTheWay.add(orders);
        return orders;
    }

    @Override
    public List<Order> acceptOrders(List<Order> orders) {
        this.ordersOnTheWay.remove(orders);
        return orders;
    }

    @Override
    public List<List<Order>> getSentOrders() {
        return ordersOnTheWay;
    }

    @Override
    public Order save(Order order) {
        orders.add(order);
        return order;
    }

    @Override
    public void delete(Order order) {
        orders.remove(order);
    }

    @Override
    public List<Order> findAll() {
        return orders;
    }

    @Override
    public Order findOne(Long id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    @Override
    public Order update(Order update) {
        return null;
    }
}
