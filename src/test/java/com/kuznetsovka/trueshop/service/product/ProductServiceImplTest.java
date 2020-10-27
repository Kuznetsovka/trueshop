package com.kuznetsovka.trueshop.service.product;

import com.kuznetsovka.trueshop.dao.ProductRepository;
import com.kuznetsovka.trueshop.domain.Product;
import com.kuznetsovka.trueshop.dto.ProductDto;
import com.kuznetsovka.trueshop.mapper.ProductMapper;
import com.kuznetsovka.trueshop.service.bucket.BucketService;
import com.kuznetsovka.trueshop.service.user.UserService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {
    private ProductServiceImpl productService;
    private ProductMapper mapper;
    private ProductRepository productRepository;
    private UserService userService;
    private BucketService bucketService;
    private SimpMessagingTemplate template;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All tests");
    }

    @BeforeEach
    void setUp() {
        System.out.println("Before each test");
        mapper = Mockito.mock(ProductMapper.class);
        productRepository = Mockito.mock(ProductRepository.class);
        bucketService = Mockito.mock(BucketService.class);
        template = Mockito.mock(SimpMessagingTemplate.class);
        userService = Mockito.mock(UserService.class);
        productService = new ProductServiceImpl(productRepository,userService,bucketService,template);
    }

    @AfterEach
    void afterEach(){
        System.out.println("After each test");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("After All test");
    }

    @Test
    void save() {
        //have
        ProductDto productDto = ProductDto.builder()
                .title("Cheese")
                .price (100.0)
                .build();
        //execute
        boolean result = productService.save (productDto);

        //check
        Assertions.assertTrue(result);
    }

    @Test
    void addToUserBucket() {
    }

    @Test
    void getAll() {
        //have
        ProductDto productDto1 = ProductDto.builder()
                .title("Cheese")
                .price (100.0)
                .build();
        ProductDto productDto2 = ProductDto.builder()
                .title("Beer")
                .price (200.0)
                .build();
        List<ProductDto> products = new ArrayList<> ();
        products.add(productDto1);
        products.add(productDto2);
        Mockito.when(productRepository.findAll ()).thenReturn (mapper.toProductList (products));

        //execute
        List<ProductDto> actualProducts = productService.getAll ();

        //check
        Assertions.assertNotNull(actualProducts);
        Assertions.assertEquals(products, actualProducts);
        Assertions.assertEquals (products.get (1).getPrice (),actualProducts.get (1).getPrice ());
        Assertions.assertEquals (products.get (0).getTitle (),actualProducts.get (0).getTitle ());
    }
}