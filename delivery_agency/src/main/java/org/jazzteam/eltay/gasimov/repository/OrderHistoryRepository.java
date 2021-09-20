package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
}
