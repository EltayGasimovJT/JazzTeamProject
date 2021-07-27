package service;

import entity.*;

import java.util.List;

public interface OrderService {
   Client.Order UpdateOrderCurrentLocation(long id, AbstractLocation newLocation);

   void updateOrderHistory(long id, OrderHistory newHistory);

   Client.Order create(Client.Order order);

   Client.Order findById(long id);

   Client.Order findByRecipient(Client recipient);

   Client.Order findBySender(Client sender);

   AbstractLocation getCurrentOrderLocation(long id);

   void send(List<Client.Order> orders, Voyage voyage);

   void accept(List<Client.Order> orders);

   String getState(long id);

   void compareOrders(List<Client.Order> expectedOrders, List<Client.Order> acceptedOrders) throws IllegalArgumentException;

   boolean isFinalWarehouse(Client.Order order);
}