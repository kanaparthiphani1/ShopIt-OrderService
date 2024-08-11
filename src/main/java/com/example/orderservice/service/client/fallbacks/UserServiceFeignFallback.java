package com.example.orderservice.service.client.fallbacks;

import com.example.orderservice.dto.userDto.User;
import com.example.orderservice.service.client.UserServiceClient;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFeignFallback implements UserServiceClient {
    @Override
    public User getUserById(Long id) {
        return new User();
    }
}
