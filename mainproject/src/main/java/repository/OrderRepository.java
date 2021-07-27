package repository;

import entity.Client;

import java.util.List;

public interface OrderRepository extends GeneralRepository<Client.Order> {
    Client.Order findByRecipient(Client recipient);

    Client.Order findBySender(Client sender);

    List<Client.Order> saveSentOrders(List<Client.Order> orders);

    List<Client.Order> acceptOrders(List<Client.Order> orders);
}
