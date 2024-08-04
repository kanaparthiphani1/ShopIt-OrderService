package com.example.orderservice.repository;

import com.example.orderservice.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findOrdersByUserId(Long userId, PageRequest pageRequest);
    Optional<Order> findOrderByIdAndUserId(Long orderId, Long userId);
}
