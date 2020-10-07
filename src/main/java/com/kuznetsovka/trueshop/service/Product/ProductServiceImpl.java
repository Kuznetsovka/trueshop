package com.kuznetsovka.trueshop.service.Product;

import com.kuznetsovka.trueshop.dao.ProductRepository;
import com.kuznetsovka.trueshop.domain.Product;
import com.kuznetsovka.trueshop.dto.ProductDto;
import com.kuznetsovka.trueshop.dto.UserDto;
import com.kuznetsovka.trueshop.mapper.ProductMapper;
import com.kuznetsovka.trueshop.service.User.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper = ProductMapper.MAPPER;
    private final ProductRepository productRepository;


    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto findById(Long id) {
        return mapper.fromProduct(productRepository.getOne(id));
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<ProductDto> findAll() {
        return mapper.fromProductList(productRepository.findAll());
    }


    @Override
    @Transactional
    public boolean save(ProductDto dto) {
        Product product = Product.builder()
                .price (dto.getPrice ())
                .categories (dto.getCategories ())
                .build();
        productRepository.save(product);
        return true;
    }

    @Transactional
    public void delete(Long id){
        productRepository.deleteById (id);
    }

}
