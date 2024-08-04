package com.example.orderservice.service.client;

import com.example.orderservice.config.BearerAuthFeignConfig;
import com.example.orderservice.dto.userDto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userservice",configuration = BearerAuthFeignConfig.class)
public interface UserServiceClient {

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") Long id);
}
