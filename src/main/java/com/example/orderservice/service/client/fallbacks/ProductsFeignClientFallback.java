package com.example.orderservice.service.client.fallbacks;

import com.example.orderservice.dto.ProductWithCategoryDTO;
import com.example.orderservice.service.client.ProductFeignClient;
import org.springframework.stereotype.Component;

@Component
public class ProductsFeignClientFallback implements ProductFeignClient {

    @Override
    public ProductWithCategoryDTO getProductById(Long id) {
        return new ProductWithCategoryDTO();
    }
}
