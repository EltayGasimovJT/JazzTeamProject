package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.Client;
import org.jazzteam.eltay.gasimov.entity.Order;

import java.util.List;

public interface OrderRepository extends GeneralRepository<Order> {
    Order findByRecipient(Client recipientForSearch);

    Order findBySender(Client senderForSearch);

    List<Order> saveSentOrders(List<Order> ordersToSend);

    List<Order> acceptOrders(List<Order> ordersToAccepted);

    List<List<Order>> getSentOrders();
}