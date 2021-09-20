package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.WorkerRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRolesRepository extends JpaRepository<WorkerRoles, Long> {
    WorkerRoles findByRole(String role);
}
