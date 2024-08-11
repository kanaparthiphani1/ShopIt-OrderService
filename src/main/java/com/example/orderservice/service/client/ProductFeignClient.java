package com.example.orderservice.service.client;

import com.example.orderservice.dto.ProductWithCategoryDTO;
import com.example.orderservice.service.client.fallbacks.ProductsFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "productservice", fallback = ProductsFeignClientFallback.class)
public interface ProductFeignClient {
    @GetMapping(value =  "/products/{id}", consumes = "application/json")
    public ProductWithCategoryDTO getProductById(@PathVariable("id") Long id);
}
