package repository;

import entity.Client;
import entity.Order;

import java.util.List;

public interface OrderRepository extends GeneralRepository<Order> {
    Order findByRecipient(Client recipient);

    Order findBySender(Client sender);

    List<Order> saveSentOrders(List<Order> orders);

    List<Order> acceptOrders(List<Order> orders);
}
