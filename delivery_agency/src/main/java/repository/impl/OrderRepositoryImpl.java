package repository.impl;

import entity.Client;
import entity.Order;
import org.springframework.stereotype.Repository;
import repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Repository(value = "orderRepository")
public class OrderRepositoryImpl implements OrderRepository {
    private final List<Order> orders = new ArrayList<>();
    private final List<List<Order>> ordersOnTheWay = new ArrayList<>();

    @Override
    public Order findByRecipient(Client recipientForSearch) {
        return orders.stream()
                .filter(order -> order.getRecipient().getId().equals(recipientForSearch.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Order findBySender(Client senderForSearch) {
        return null;
    }

    @Override
    public List<Order> saveSentOrders(List<Order> ordersToSend) {
        this.ordersOnTheWay.add(ordersToSend);
        return ordersToSend;
    }

    @Override
    public List<Order> acceptOrders(List<Order> ordersToAccept) {
        this.ordersOnTheWay.remove(ordersToAccept);
        return ordersToAccept;
    }

    @Override
    public List<List<Order>> getSentOrders() {
        return ordersOnTheWay;
    }

    @Override
    public Order save(Order orderToSave) {
        orders.add(orderToSave);
        return orderToSave;
    }

    @Override
    public void delete(Long idForDelete) {
        orders.remove(findOne(idForDelete));
    }

    @Override
    public List<Order> findAll() {
        return orders;
    }

    @Override
    public Order findOne(Long idForSearch){
        return orders.stream()
                .filter(order -> order.getId().equals(idForSearch))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Order update(Order newOrder) {
        Order orderToSave = findOne(newOrder.getId());
        orders.remove(orderToSave);
        orderToSave.setHistory(newOrder.getHistory());
        orderToSave.setState(newOrder.getState());
        orderToSave.setCurrentLocation(newOrder.getCurrentLocation());
        orderToSave.setDestinationPlace(newOrder.getDestinationPlace());
        orderToSave.setParcelParameters(newOrder.getParcelParameters());
        orderToSave.setRecipient(newOrder.getRecipient());
        orderToSave.setSender(newOrder.getSender());
        orderToSave.setPrice(newOrder.getPrice());
        orders.add(orderToSave);
        return orderToSave;
    }
}