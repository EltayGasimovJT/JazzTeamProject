package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStateRepository extends JpaRepository<OrderState, Long> {
    OrderState findByState(String stateForFind);
}
