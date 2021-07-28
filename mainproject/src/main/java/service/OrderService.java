package service;

import entity.*;

import java.util.List;

public interface OrderService {
   Order UpdateOrderCurrentLocation(long id, AbstractLocation newLocation);

   void updateOrderHistory(long id, OrderHistory newHistory);

   Order create(Order order);

   Order findById(long id);

   Order findByRecipient(Client recipient);

   Order findBySender(Client sender);

   AbstractLocation getCurrentOrderLocation(long id);

   void send(List<Order> orders, Voyage voyage);

   void accept(List<Order> orders);

   String getState(long id);

   void compareOrders(List<Order> expectedOrders, List<Order> acceptedOrders) throws IllegalArgumentException;

   boolean isFinalWarehouse(Order order);
}