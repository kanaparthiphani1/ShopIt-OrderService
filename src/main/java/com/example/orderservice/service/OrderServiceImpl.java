package com.example.orderservice.service;

import com.example.orderservice.dto.CreateOrderDto;
import com.example.orderservice.dto.ProductWithCategoryDTO;
import com.example.orderservice.dto.SendEmailDto;
import com.example.orderservice.dto.userDto.User;
import com.example.orderservice.models.Order;
import com.example.orderservice.models.OrderItems;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.client.ProductFeignClient;
import com.example.orderservice.service.client.UserServiceClient;
import com.example.orderservice.utils.OrderStatus;
import com.example.orderservice.utils.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;
    private ProductFeignClient productFeignClient;
    private UserServiceClient userServiceClient;
    private StreamBridge streamBridge;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductFeignClient productFeignClient,UserServiceClient userServiceClient,StreamBridge streamBridge) {
        this.orderRepository = orderRepository;
        this.productFeignClient = productFeignClient;
        this.userServiceClient = userServiceClient;
        this.streamBridge = streamBridge;
    }

    @Override
    public Order createOrder(CreateOrderDto order, Long userId) {
        List<OrderItems> orderItems = new ArrayList<>();
        AtomicReference<Double> totalPrice = new AtomicReference<>((double) 0);
        order.getOrderItems().forEach(item->{
            OrderItems orderItem = new OrderItems();
            ProductWithCategoryDTO prodWithCat = productFeignClient.getProductById(item.getProdId());
            orderItem.setName(prodWithCat.getTitle());
            orderItem.setPrice(prodWithCat.getPrice());
            orderItem.setQuantity(item.getQuantity());
            totalPrice.set(totalPrice.get() + (orderItem.getQuantity() * orderItem.getPrice()));
            orderItems.add(orderItem);
        });
        Order order1 = new Order();
        order1.setOrderItemsList(orderItems);
        order1.setPrice(totalPrice.get());
        order1.setUserId(userId);
        order1.setStatus(OrderStatus.ORDER_COMPLETED);
        order1.setPaymentStatus(PaymentStatus.PENDING);

        return orderRepository.save(order1);
    }

    @Override
    public Page<Order> getAllOrdersByUserId(int pageNum, int pageSize, String sortDir, Long userId) {
        return orderRepository.findOrdersByUserId(userId, PageRequest.of(pageNum,pageSize,sortDir.equalsIgnoreCase("asc")?
                Sort.by("createdAt").ascending() :
                Sort.by("createdAt").descending()));
    }

    @Override
    public Page<Order> getAllOrders(int pageNum, int pageSize, String sortDir) {
        return orderRepository.findAll(PageRequest.of(pageNum,pageSize,sortDir.equalsIgnoreCase("asc")?
                Sort.by("createdAt").ascending() :
                Sort.by("createdAt").descending()));
    }

    @Override
    public String cancelOrder(Long orderId, Long userId) {
        Order order = orderRepository.findOrderByIdAndUserId(orderId,userId).orElse(null);
        if(order==null){
            return "Order Not Found";
        }
        if(!order.getStatus().equals(OrderStatus.ORDER_COMPLETED)){
            return "Order Not Completed";
        }

        order.setStatus(OrderStatus.ORDER_CANCELLED);
        orderRepository.save(order);

        return "Order Cancelled";
    }

    @Override
    public Order getOrderById(Long orderId, Long userId) {
        return orderRepository.findOrderByIdAndUserId(orderId,userId).orElse(null);
    }

    @Override
    public String paymentSuccess(Long orderId, Long userId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()){
            return "Order Not Found";
        }
        Order order1 = order.get();
        order1.setPaymentStatus(PaymentStatus.SUCCESSFULL);

        User user = this.userServiceClient.getUserById(order1.getUserId());


        String body = "Hi "+user.getName()+",\n" +
                "Your Order id: "+orderId+" is Confirmed.\n" +
                "Thanks for shopping in ShopIT.";
        this.streamBridge.send("sendEmail-out-0", new SendEmailDto(user.getEmail(),"random","Order Confirmation", body));

        orderRepository.save(order1);
        return "DOne";
    }


}
