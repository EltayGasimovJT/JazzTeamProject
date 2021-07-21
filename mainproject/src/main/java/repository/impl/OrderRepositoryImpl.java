package repository.impl;

import entity.Client;
import repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderRepositoryImpl implements OrderRepository {
    private final List<Client.Order> orders = new ArrayList<>();

    @Override
    public List<Client.Order> findAllByClientId(Client.Order order) {
        return orders.stream()
                .filter(order1 -> order1.getClient().getPassportId().equals(order.getClient().getPassportId()))
                .collect(Collectors.toList());
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
    public Client.Order findOne(String string) {
        return orders.stream()
                .filter(order -> order.getOrderState().equals(string))
                .findFirst()
                .orElse(null);
    }
}
