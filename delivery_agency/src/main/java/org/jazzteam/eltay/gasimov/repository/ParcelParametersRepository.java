package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.ParcelParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelParametersRepository extends JpaRepository<ParcelParameters, Long> {
}
