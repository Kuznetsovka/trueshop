package com.kuznetsovka.trueshop.service.product;

import com.kuznetsovka.trueshop.dao.ProductRepository;
import com.kuznetsovka.trueshop.domain.Bucket;
import com.kuznetsovka.trueshop.domain.Product;
import com.kuznetsovka.trueshop.domain.User;
import com.kuznetsovka.trueshop.dto.ProductDto;
import com.kuznetsovka.trueshop.mapper.ProductMapper;
import com.kuznetsovka.trueshop.service.User.UserService;
import com.kuznetsovka.trueshop.service.bucket.BucketService;
import com.kuznetsovka.trueshop.service.measure.MeasureMethod;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final SimpMessagingTemplate template;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, BucketService bucketService, SimpMessagingTemplate template) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
        this.template = template;
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

    @MeasureMethod
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

    @Override
    @Transactional
    public void addProduct(ProductDto dto) {
        Product product = ProductMapper.MAPPER.toProduct(dto);
        Product savedProduct = productRepository.save(product);
        template.convertAndSend("/topic/product",
                ProductMapper.MAPPER.fromProduct(savedProduct));
    }


    @Override
    public Long getId(ProductDto updateProduct) {
        return mapper.toProduct (updateProduct).getId ();
    }

    @Transactional
    public void delete(Long id){
        productRepository.deleteById (id);
    }

}
