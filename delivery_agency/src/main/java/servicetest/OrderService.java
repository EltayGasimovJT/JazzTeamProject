package servicetest;

import entity.*;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
   Order updateOrderCurrentLocation(long id, AbstractLocation newLocation);

   void updateOrderHistory(long id, OrderHistory newHistory);

   Order create(Order order);

   Order findById(long id);

   Order findByRecipient(Client recipient);

   Order findBySender(Client sender);

   AbstractLocation getCurrentOrderLocation(long id);

   void send(List<Order> orders, Voyage voyage);

   List<Order> accept(List<Order> orders);

   String getState(long id);

   void compareOrders(List<Order> expectedOrders, List<Order> acceptedOrders) throws IllegalArgumentException;

   boolean isFinalWarehouse(Order order);

   List<Order> findAll();

   BigDecimal calculatePrice(Order order) throws IllegalArgumentException;

   List<List<Order>> getOrdersOnTheWay();

   Order update(Order order);

   void delete(Order order);
}