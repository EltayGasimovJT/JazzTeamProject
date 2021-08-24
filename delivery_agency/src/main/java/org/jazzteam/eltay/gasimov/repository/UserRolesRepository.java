package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
    public UserRoles findByRole(String role);
}
