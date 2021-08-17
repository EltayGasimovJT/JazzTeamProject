package service;

import dto.AbstractBuildingDto;
import dto.ClientDto;
import dto.OrderDto;
import dto.OrderHistoryDto;
import entity.AbstractBuilding;
import entity.Order;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface OrderService {
   Order updateOrderCurrentLocation(long idForLocationUpdate, AbstractBuildingDto newLocation) throws SQLException;

   void updateOrderHistory(long idForHistoryUpdate, OrderHistoryDto newHistory) throws SQLException;

   Order save(OrderDto orderDtoToSave) throws SQLException;

   Order findOne(long idForSearch) throws SQLException;

   Order findByRecipient(ClientDto recipientForSearch);

   Order findBySender(ClientDto senderForSearch);

   AbstractBuilding getCurrentOrderLocation(long idForFindCurrentLocation) throws SQLException;

   void send(List<OrderDto> orderDtosToSend) throws SQLException;

   List<Order> accept(List<OrderDto> orderDtosToAccept) throws SQLException;

   String getState(long idForStateFind) throws SQLException;

   void compareOrders(List<OrderDto> expectedOrders, List<OrderDto> acceptedOrders) throws IllegalArgumentException;

   List<Order> findAll() throws SQLException;

   BigDecimal calculatePrice(OrderDto orderForCalculate) throws IllegalArgumentException, SQLException;

   List<List<Order>> getOrdersOnTheWay();

   Order update(OrderDto orderDtoToUpdate) throws SQLException;

   void delete(Long idForDelete);
}