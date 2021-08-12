package service;

import dto.OrderDto;
import entity.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface OrderService {
   OrderDto updateOrderCurrentLocation(long id, AbstractLocation newLocation) throws SQLException;

   void updateOrderHistory(long id, OrderHistory newHistory) throws SQLException;

   OrderDto create(OrderDto order) throws SQLException;

   OrderDto findById(long id) throws SQLException;

   OrderDto findByRecipient(Client recipient);

   OrderDto findBySender(Client sender);

   AbstractLocation getCurrentOrderLocation(long id) throws SQLException;

   void send(List<OrderDto> orders, Voyage voyage) throws SQLException;

   List<OrderDto> accept(List<OrderDto> orders) throws SQLException;

   String getState(long id) throws SQLException;

   void compareOrders(List<OrderDto> expectedOrders, List<OrderDto> acceptedOrders) throws IllegalArgumentException;

   boolean isFinalWarehouse(OrderDto order);

   List<OrderDto> findAll() throws SQLException;

   BigDecimal calculatePrice(OrderDto order) throws IllegalArgumentException, SQLException;

   List<List<OrderDto>> getOrdersOnTheWay();

   OrderDto update(OrderDto order) throws SQLException;

   void delete(Long id);
}