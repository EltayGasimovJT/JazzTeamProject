package service;

import entity.AbstractLocation;
import entity.Client;
import entity.Voyage;

import java.util.List;

public interface OrderService {
    Client.Order findById(long id);

    Client.Order create(Client.Order order);

    List<Client.Order> findByRecipient(Client client);

    List<Client.Order> findBySender(Client client);

    Client.Order cancelOrder(long id);

    AbstractLocation getCurrentOrderLocation(long id);

    List<AbstractLocation> getRoute(long sendingPointId, long acceptingPointId);

    void send(Voyage voyage);

    void accept(Voyage voyage);

    String getState(long id);

    Client.Order compareOrders(List<Client.Order> actualOrders, List<Client.Order> expectedOrders);
}