package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.AbstractBuildingDto;
import org.jazzteam.eltay.gasimov.dto.ClientDto;
import org.jazzteam.eltay.gasimov.dto.OrderDto;
import org.jazzteam.eltay.gasimov.dto.OrderHistoryDto;
import org.jazzteam.eltay.gasimov.entity.AbstractBuilding;
import org.jazzteam.eltay.gasimov.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
   Order updateOrderCurrentLocation(long idForLocationUpdate, AbstractBuildingDto newLocation);

   void updateOrderHistory(long idForHistoryUpdate, OrderHistoryDto newHistory);

   Order save(OrderDto orderDtoToSave);

   Order findOne(long idForSearch);

   Order findByRecipient(ClientDto recipientForSearch);

   Order findBySender(ClientDto senderForSearch);

   AbstractBuilding getCurrentOrderLocation(long idForFindCurrentLocation);

   void send(List<OrderDto> orderDtosToSend);

   List<Order> accept(List<OrderDto> orderDtosToAccept);

   String getState(long idForStateFind);

   void compareOrders(List<OrderDto> expectedOrders, List<OrderDto> acceptedOrders) throws IllegalArgumentException;

   List<Order> findAll();

   BigDecimal calculatePrice(OrderDto orderForCalculate) throws IllegalArgumentException;

   Order update(OrderDto orderDtoToUpdate);

   void delete(Long idForDelete);
}