package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.OrderProcessingPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProcessingPointRepository extends JpaRepository<OrderProcessingPoint, Long> {
    OrderProcessingPoint findByLocation(String location);
}