package service;

import dto.AbstractBuildingDto;
import dto.ClientDto;
import dto.OrderDto;
import dto.OrderHistoryDto;
import entity.AbstractBuilding;
import entity.Order;
import entity.OrderHistory;
import entity.Voyage;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface OrderService {
   Order updateOrderCurrentLocation(long id, AbstractBuildingDto newLocation) throws SQLException;

   void updateOrderHistory(long id, OrderHistoryDto newHistory) throws SQLException;

   Order create(OrderDto order) throws SQLException;

   Order findById(long id) throws SQLException;

   Order findByRecipient(ClientDto recipient);

   Order findBySender(ClientDto sender);

   AbstractBuilding getCurrentOrderLocation(long id) throws SQLException;

   void send(List<OrderDto> orders) throws SQLException;

   List<Order> accept(List<OrderDto> orders) throws SQLException;

   String getState(long id) throws SQLException;

   void compareOrders(List<OrderDto> expectedOrders, List<OrderDto> acceptedOrders) throws IllegalArgumentException;

   List<Order> findAll() throws SQLException;

   BigDecimal calculatePrice(OrderDto order) throws IllegalArgumentException, SQLException;

   List<List<Order>> getOrdersOnTheWay();

   Order update(OrderDto order) throws SQLException;

   void delete(Long id);
}