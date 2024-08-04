package com.example.orderservice.service.client;

import com.example.orderservice.dto.ProductWithCategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("productservice")
public interface ProductFeignClient {
    @GetMapping(value =  "/products/{id}", consumes = "application/json")
    public ProductWithCategoryDTO getProductById(@PathVariable("id") Long id);
}
