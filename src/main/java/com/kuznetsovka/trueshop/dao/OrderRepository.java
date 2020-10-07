package com.kuznetsovka.trueshop.dao;


import com.kuznetsovka.trueshop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
