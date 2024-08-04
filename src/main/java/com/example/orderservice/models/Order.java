package com.example.orderservice.models;

import com.example.orderservice.utils.OrderStatus;
import com.example.orderservice.utils.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "'orders'")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseModel{

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<OrderItems> orderItemsList;

    private Long userId;

    private double price;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status;

    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;

}
