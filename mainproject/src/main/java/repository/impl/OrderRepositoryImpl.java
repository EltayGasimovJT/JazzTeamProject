package repository.impl;

import entity.Client;
import repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    private final List<Client.Order> orders = new ArrayList<>();
    private final List<List<Client.Order>> ordersOnTheWay = new ArrayList<>();

    @Override
    public Client.Order findByRecipient(Client recipient) {
        return orders.stream()
                .filter(order1 -> order1.getRecipient().getId() == recipient.getId())
                .findFirst()
                .orElse(null);
    }

    @Override
    public Client.Order findBySender(Client sender) {
        return null;
    }

    @Override
    public List<Client.Order> saveSentOrders(List<Client.Order> orders) {
        this.ordersOnTheWay.add(orders);
        return orders;
    }

    @Override
    public List<Client.Order> acceptOrders(List<Client.Order> orders) {
        this.ordersOnTheWay.remove(orders);
        return orders;
    }

    @Override
    public Client.Order save(Client.Order order) {
        orders.add(order);
        return order;
    }

    @Override
    public void delete(Client.Order order) {
        orders.remove(order);
    }

    @Override
    public List<Client.Order> findAll() {
        return orders;
    }

    @Override
    public Client.Order findOne(long id) {
        return orders.stream()
                .filter(order -> order.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Client.Order update(Client.Order update) {
        return null;
    }
}
