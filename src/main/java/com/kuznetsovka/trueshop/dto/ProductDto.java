package com.kuznetsovka.trueshop.dto;

import com.kuznetsovka.trueshop.domain.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private Double price;
    private List<Category> categories;
}
