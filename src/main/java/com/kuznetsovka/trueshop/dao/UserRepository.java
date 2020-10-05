package com.kuznetsovka.trueshop.dao;

import com.kuznetsovka.trueshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByName(String name);
}
