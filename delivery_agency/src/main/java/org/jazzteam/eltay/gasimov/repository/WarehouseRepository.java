package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Warehouse findByLocation(String location);
}