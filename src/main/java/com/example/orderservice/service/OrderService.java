package com.example.orderservice.service;

import com.example.orderservice.dto.CreateOrderDto;
import com.example.orderservice.models.Order;
import org.springframework.data.domain.Page;

public interface OrderService {
    public Order createOrder(CreateOrderDto order, Long userId);
    public Page<Order> getAllOrdersByUserId(int pageNum, int pageSize,String sortDir,Long userId);
    public Page<Order> getAllOrders(int pageNum, int pageSize,String sortDir);
    public String cancelOrder(Long orderId, Long userId);
    public Order getOrderById(Long orderId, Long userId);
    public String paymentSuccess(Long orderId, Long userId);
}
