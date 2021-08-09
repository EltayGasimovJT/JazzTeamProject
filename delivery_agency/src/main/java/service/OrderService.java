package service;

import entity.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface OrderService {
   Order updateOrderCurrentLocation(long id, AbstractLocation newLocation) throws SQLException;

   void updateOrderHistory(long id, OrderHistory newHistory) throws SQLException;

   Order create(Order order) throws SQLException;

   Order findById(long id) throws SQLException;

   Order findByRecipient(Client recipient);

   Order findBySender(Client sender);

   AbstractLocation getCurrentOrderLocation(long id) throws SQLException;

   void send(List<Order> orders, Voyage voyage) throws SQLException;

   List<Order> accept(List<Order> orders) throws SQLException;

   String getState(long id) throws SQLException;

   void compareOrders(List<Order> expectedOrders, List<Order> acceptedOrders) throws IllegalArgumentException;

   boolean isFinalWarehouse(Order order);

   List<Order> findAll() throws SQLException;

   BigDecimal calculatePrice(Order order) throws IllegalArgumentException;

   List<List<Order>> getOrdersOnTheWay();

   Order update(Order order) throws SQLException;

   void delete(Order order);
}