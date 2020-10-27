package com.kuznetsovka.trueshop.dao;

import com.kuznetsovka.trueshop.domain.Product;
import com.kuznetsovka.trueshop.service.product.ProductService;
import com.kuznetsovka.trueshop.service.user.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:initProducts.sql")})
class ProductRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

//    @MockBean
//    private ProductService productService;
//
//    @MockBean
//    private UserService userService;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All tests");
    }

    @BeforeEach
    void setUp() {
        System.out.println("Before each test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each test");
    }

    @Test
    void checkFindAllByPriceBetween() {
        //have
//        values (3, 'Milk', 65.0);
//        values (5, 'Bread', 58.0);
        List<Product> actualProducts = productRepository.findAllByPriceBetween(50.0,100.0);

        //check
        Assertions.assertNotNull(actualProducts);
        Assertions.assertTrue (actualProducts.size ()==2);
        Assertions.assertArrayEquals (new String[]{"Milk", "Bread"}, new String[]{actualProducts.get (0).getTitle (), actualProducts.get (1).getTitle ()});
        Assertions.assertEquals(65.0,actualProducts.get (0).getPrice ());
        Assertions.assertEquals(58.0,actualProducts.get (1).getPrice ());
    }
}