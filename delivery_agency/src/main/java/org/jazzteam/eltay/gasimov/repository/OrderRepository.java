package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByRecipientId(Long recipientId);

    Order findBySenderId(Long senderId);

    Order findByTrackNumber(String trackNumber);

    void deleteByTrackNumber(String orderNumber);
}