package service;

import entity.Client;

public interface OrderService {
    void saveClient(Client client);

    double findCost(Client client);

    String updateOrderState(Client client);

    Client.Order findOrder(Client client);

    boolean isMatches(Client.Order actualOrder, Client.Order expectedOrder);

    Client.Order measureOrderParameters(Client client);

    Client.Order createNewOrder(Client client);

    void sendOrder(Client.Order order);

    boolean acceptTheOrder(Client.Order order);

    Client.Order giveOrderToTheClient(Client client);

    String checkTheOrderState(Client.Order order);

    boolean checkOrderParameters(Client.Order order);
}