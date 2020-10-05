package com.kuznetsovka.trueshop.service;

import com.kuznetsovka.trueshop.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto findById(Long id);
    List<ProductDto> findAll();
    ProductDto save(ProductDto productDto);
    void delete(Long id);
    void saveAndSet(ProductDto productDto);
}
