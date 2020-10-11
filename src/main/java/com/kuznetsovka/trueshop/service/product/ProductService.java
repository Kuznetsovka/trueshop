package com.kuznetsovka.trueshop.service.product;
import com.kuznetsovka.trueshop.domain.Product;
import com.kuznetsovka.trueshop.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto findById(Long id);
    Product getById(Long id);
    List<ProductDto> getAll();
    boolean save(ProductDto dto);
    void delete(Long id);
    void addToUserBucket(Long productId, String username);
}
