package com.example.orderservice.controllers;

import com.example.orderservice.dto.CreateOrderDto;
import com.example.orderservice.models.Order;
import com.example.orderservice.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderDto order, @AuthenticationPrincipal Jwt jwt) {
        Long userId = (Long) jwt.getClaims().get("userId");
        return ResponseEntity.ok(orderService.createOrder(order, userId));
    }

    @GetMapping()
    public ResponseEntity<Page<Order>> getAllOrdersByUserId(@RequestParam("pageNum") int pageNum,
                                                            @RequestParam("pageSize") int pageSize,
                                                            @RequestParam("sortDir") String sortDir,
                                                            @AuthenticationPrincipal Jwt jwt
                                                            ){
        Long userId = (Long) jwt.getClaims().get("userId");
        return ResponseEntity.ok(orderService.getAllOrdersByUserId(pageNum,pageSize,sortDir,userId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Page<Order>> getAllOrders(@RequestParam("pageNum") int pageNum,
                                                            @RequestParam("pageSize") int pageSize,
                                                            @RequestParam("sortDir") String sortDir
    ){
        return ResponseEntity.ok(orderService.getAllOrders(pageNum,pageSize,sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long orderId, @AuthenticationPrincipal Jwt jwt) {
        Long userId = (Long) jwt.getClaims().get("userId");
        return ResponseEntity.ok(orderService.getOrderById(orderId, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt){
        Long userId = (Long) jwt.getClaims().get("userId");
        return ResponseEntity.ok(orderService.cancelOrder(id,userId));
    }

    @PutMapping("/payment/{id}")
    public ResponseEntity<String> paymentSuccess(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt){
        Long userId = (Long) jwt.getClaims().get("userId");
        return ResponseEntity.ok(orderService.paymentSuccess(id,userId));
    }


}
