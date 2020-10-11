package com.kuznetsovka.trueshop.service.product;

import com.kuznetsovka.trueshop.dao.ProductRepository;
import com.kuznetsovka.trueshop.domain.Bucket;
import com.kuznetsovka.trueshop.domain.Product;
import com.kuznetsovka.trueshop.domain.User;
import com.kuznetsovka.trueshop.dto.ProductDto;
import com.kuznetsovka.trueshop.mapper.ProductMapper;
import com.kuznetsovka.trueshop.service.User.UserService;
import com.kuznetsovka.trueshop.service.bucket.BucketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper = ProductMapper.MAPPER;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, BucketService bucketService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
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
    public List<ProductDto> getAll() {
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

    @Override
    @Transactional
    public void addToUserBucket(Long productId, String username) {
        User user = userService.findByName(username);
        if(user == null){
            throw new RuntimeException("User not found. " + username);
        }

        Bucket bucket = user.getBucket();
        if(bucket == null){
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucket(newBucket);
            userService.save(user);
        }
        else {
            bucketService.addProducts(bucket, Collections.singletonList(productId));
        }
    }

    @Transactional
    public void delete(Long id){
        productRepository.deleteById (id);
    }

}
