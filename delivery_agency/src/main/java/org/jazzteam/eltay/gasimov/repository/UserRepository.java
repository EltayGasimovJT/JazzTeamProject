package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}