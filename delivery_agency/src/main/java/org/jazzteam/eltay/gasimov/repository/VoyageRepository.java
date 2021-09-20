package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Long> {
}