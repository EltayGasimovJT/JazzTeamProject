package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.Client;
import org.jazzteam.eltay.gasimov.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByRecipient(Client recipientForSearch);

    Order findBySender(Client senderForSearch);
}