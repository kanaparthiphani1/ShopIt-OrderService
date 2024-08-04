package com.example.orderservice.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductWithCategoryDTO implements Serializable {
    private String title;
    private String description;
    private Double price;
    private String category;
}
