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
         List<Client.Order> resultOrders = orders.stream()
                .filter(order1 -> order.getClient().getPassportId().equals(order.getClient().getPassportId()))
                .collect(Collectors.toList());
        return resultOrders;
    }

    @Override
    public Client.Order save(Client.Order order) {
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
    public Client.Order findOne(int num) {
        return null;
    }
}
