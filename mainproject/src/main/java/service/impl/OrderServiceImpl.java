package service.impl;

import entity.Client;
import service.OrderService;

public class OrderServiceImpl implements OrderService {
    @Override
    public void saveClient(Client client) {

    }

    @Override
    public double findCost(Client client) {
        return 0;
    }

    @Override
    public String updateOrderState(Client client) {
        return null;
    }

    @Override
    public Client.Order findOrder(Client client) {
        return null;
    }

    @Override
    public boolean isMatches(Client.Order actualOrder, Client.Order expectedOrder) {
        return false;
    }

    @Override
    public Client.Order measureOrderParameters(Client client) {
        return null;
    }

    @Override
    public Client.Order createNewOrder(Client client) {
        return null;
    }

    @Override
    public void sendOrder(Client.Order order) {

    }

    @Override
    public boolean acceptTheOrder(Client.Order order) {
        return false;
    }

    @Override
    public Client.Order giveOrderToTheClient(Client client) {
        return null;
    }

    @Override
    public String checkTheOrderState(Client.Order order) {
        return null;
    }

    @Override
    public boolean checkOrderParameters(Client.Order order) {
        return false;
    }
}
