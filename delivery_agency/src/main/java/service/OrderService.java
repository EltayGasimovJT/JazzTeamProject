package service;

import dto.AbstractBuildingDto;
import dto.ClientDto;
import dto.OrderDto;
import dto.OrderHistoryDto;
import entity.OrderHistory;
import entity.Voyage;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface OrderService {
   OrderDto updateOrderCurrentLocation(long id, AbstractBuildingDto newLocation) throws SQLException;

   void updateOrderHistory(long id, OrderHistoryDto newHistory) throws SQLException;

   OrderDto create(OrderDto order) throws SQLException;

   OrderDto findById(long id) throws SQLException;

   OrderDto findByRecipient(ClientDto recipient);

   OrderDto findBySender(ClientDto sender);

   AbstractBuildingDto getCurrentOrderLocation(long id) throws SQLException;

   void send(List<OrderDto> orders) throws SQLException;

   List<OrderDto> accept(List<OrderDto> orders) throws SQLException;

   String getState(long id) throws SQLException;

   void compareOrders(List<OrderDto> expectedOrders, List<OrderDto> acceptedOrders) throws IllegalArgumentException;

   List<OrderDto> findAll() throws SQLException;

   BigDecimal calculatePrice(OrderDto order) throws IllegalArgumentException, SQLException;

   List<List<OrderDto>> getOrdersOnTheWay();

   OrderDto update(OrderDto order) throws SQLException;

   void delete(Long id);
}