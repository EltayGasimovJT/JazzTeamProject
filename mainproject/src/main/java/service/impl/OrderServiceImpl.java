package service.impl;

import entity.AbstractLocation;
import entity.Client;
import entity.ParcelParameters;
import entity.Voyage;
import service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    @Override
    public Client.Order findById(long id) {
        return null;
    }

    @Override
    public Client.Order create(Client.Order order) {
        return null;
    }

    @Override
    public List<Client.Order> findByRecipient(Client client) {
        return null;
    }

    @Override
    public List<Client.Order> findBySender(Client client) {
        return null;
    }

    @Override
    public Client.Order cancelOrder(long id) {
        return null;
    }

    @Override
    public AbstractLocation getCurrentOrderLocation(long id) {
        return null;
    }

    @Override
    public List<AbstractLocation> getRoute(long sendingPointId, long acceptingPointId) {
        return null;
    }

    @Override
    public void send(Voyage voyage) {

    }

    @Override
    public void accept(Voyage voyage) {

    }

    @Override
    public String getState(long id) {
        return null;
    }

    @Override
    public Client.Order compareOrders(List<Client.Order> actualOrders, List<Client.Order> expectedOrders) {
        return null;
    }

    private Client.Order updateState(Client.Order order) {
        return null;
    }

    private Client.Order calculatePrice(ParcelParameters parcelParameters){
        return null;
    }

}
