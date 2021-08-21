package org.jazzteam.eltay.gasimov.repository;

import org.jazzteam.eltay.gasimov.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}