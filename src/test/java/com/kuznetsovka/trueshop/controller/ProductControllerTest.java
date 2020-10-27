package com.kuznetsovka.trueshop.controller;

import com.kuznetsovka.trueshop.dao.ProductRepository;
import com.kuznetsovka.trueshop.domain.Category;
import com.kuznetsovka.trueshop.domain.Product;
import com.kuznetsovka.trueshop.dto.ProductDto;
import com.kuznetsovka.trueshop.service.SessionObjectHolder;
import com.kuznetsovka.trueshop.service.product.ProductService;
import com.kuznetsovka.trueshop.service.user.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @MockBean
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private List<Product> products;
    @MockBean
    private SessionObjectHolder sessionObjectHolder;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ProductDto dto1 = new ProductDto (1L, "Cheese", 450.0, null);
    private ProductDto dto2 = new ProductDto (2L, "Beer", 45.0, null);

    @BeforeEach
    void setUp() {
        given (productService.getAll ()).willReturn (Arrays.asList (dto1, dto2));
    }

    @Test
    void checkList() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content().string(Matchers.containsString("<td>" + dto1.getTitle() + "</td>")))
                .andExpect(MockMvcResultMatchers
                        .content().string(Matchers.containsString("<td>" + dto2.getTitle() + "</td>")));

    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/" + dto1.getId ())
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content().string(Matchers.containsString("<td>" + dto1.getTitle() + "</td>")));

    }

    @Test
    void apiPrice() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("products/" + dto1.getId () + "/price")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers
                        .content().string(Matchers.containsString(String.valueOf (dto1.getPrice()))));

    }
}
