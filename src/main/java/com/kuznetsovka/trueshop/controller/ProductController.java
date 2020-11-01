package com.kuznetsovka.trueshop.controller;

import com.kuznetsovka.trueshop.dao.ProductRepository;
import com.kuznetsovka.trueshop.domain.Product;
import com.kuznetsovka.trueshop.dto.EntityNotFoundResponse;
import com.kuznetsovka.trueshop.dto.ProductDto;
import com.kuznetsovka.trueshop.exception.EntityNotFoundException;
import com.kuznetsovka.trueshop.service.SessionObjectHolder;
import com.kuznetsovka.trueshop.service.product.ProductService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private List<Product> products;
    private final SessionObjectHolder sessionObjectHolder;


    public ProductController(ProductService productService, ProductRepository productRepository, SessionObjectHolder sessionObjectHolder) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.sessionObjectHolder = sessionObjectHolder;
    }

    // http://localhost:8090/app/products - GET
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model){
        products = productRepository.findAll ();
        model.addAttribute("products", products);
        return "products";
    }

    // http://localhost:8090/products/{id}/bucket
    @RequestMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal){
        sessionObjectHolder.addClick();
        if(principal == null){
            return "redirect:/products";
        }
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/products";
    }

    @MessageMapping("/product")
    public void messageAddProduct(ProductDto dto){
        productService.addProduct(dto);
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(ProductDto dto){
        productService.addProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // http://localhost:8090/products/1 - GET
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getById(Model model, @PathVariable("id") Long id){
        checkById(id);
        List<Product> byId = productRepository.findAllById (id);
        model.addAttribute("product",
                byId == null ? new Product(): byId.get (0));
        return "product";
    }

    // http://localhost:8090/products/1/price - GET
    @RequestMapping(value = "/{id}/price", method = RequestMethod.GET)
    @ResponseBody
    public String apiPrice(@PathVariable Long id){
        checkById(id);
        List<Product> byId = productRepository.findAllById (id);
        return String.valueOf(byId == null ? null : byId.get (0).getPrice());
    }

    // http://localhost:8090/products/new - GET
    @GetMapping("/new")
    public String getFormNewProduct(Model model){
        model.addAttribute("product", new Product());
        return "new-product";
    }

    // http://localhost:8090/products/new - POST
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addNewProduct(Product savedProduct){
        productRepository.save(savedProduct);
        System.out.println(savedProduct);
        return "redirect:/products/" + savedProduct.getId();
    }

    // http://localhost:8090/products/update?id=3 - GET
    @GetMapping("/update")
    public String getFormUpdateProduct(Model model,@RequestParam(name = "id") long id){
        Product byId = productService.getById (id);
        model.addAttribute("product",
                byId == null ? new ProductDto(): byId);
        return "update-product";
    }

    // http://localhost:8090/products/update - POST
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateProduct(ProductDto updateProduct){
        productService.save (updateProduct);
        System.out.println(updateProduct);
        return "redirect:/products/" + productService.getId (updateProduct);
    }

    // http://localhost:8090/products/any
    @RequestMapping(value = "any")
    @ResponseBody
    public String anyRequest(){
        return "any request " + UUID.randomUUID().toString();
    }

    // http://localhost:8090/products/like?name=Product_7 {name:"asd"}
    @GetMapping("/like")
    public String filterByName(Model model,
                               @RequestParam String name){
        List<Product> products = productRepository.findAllByTitleLike (name);
        model.addAttribute("products", products);
        return "products";
    }

    // http://localhost:8090/products?priceFrom=500&priceTo=700
    @GetMapping(params = {"priceFrom", "priceTo"})
    public String productsByPrice(Model model,
                                  @RequestParam double priceFrom,
                                  @RequestParam double priceTo){
        List<Product> products = productRepository.findAllByPriceBetween (priceFrom, priceTo);
        model.addAttribute("products", products);
        return "products";
    }

    // http://localhost:8090/products/filter?priceFrom=500&priceTo=700
    @GetMapping("/filter")
    public String filterByPrice(Model model,
                                @RequestParam double priceFrom,
                                @RequestParam(required = false) Double priceTo){
        List<Product> products = productRepository.findAllByPriceBetween (priceFrom, priceTo == null ? Double.MAX_VALUE : priceTo);
        model.addAttribute("products", products);
        return "products";
    }

    // http://localhost:8090/products/{id}/delete
    @RequestMapping(value="/{id}/delete", method = RequestMethod.POST)
    public String removeById(Model model, @PathVariable Long id){
        checkById(id);
        products.remove (productService.findById (id));
        productRepository.deleteById (id);
        System.out.println("Удален продукт с id" + id);
        model.addAttribute("products", products);
        return "redirect:/products";
    }

    // http://localhost:8090/products/{id}/delete
    @RequestMapping(value="/{id}/delete", method = RequestMethod.GET)
    public String getRemoveById(Model model, @PathVariable Long id){
        checkById(id);
        products.remove (productService.findById (id));
        productRepository.deleteById (id);
        System.out.println("Удален продукт с id" + id);
        model.addAttribute("products", products);
        return "redirect:/products";
    }

    // http://localhost:8090/products/maxupprice - GET
    @RequestMapping("/maxupprice")
    public String filterByMaxPriceProduct(Model model){
        List<Product> products = productRepository.findAll (Sort.by("price").descending ());
        model.addAttribute("products", products);
        return "products";
    }

    // http://localhost:8090/products/minupprice - GET
    @RequestMapping("/minupprice")
    public String filterByMinPriceProduct(Model model){
        List<Product> products = productRepository.findAll (Sort.by("price").ascending());
        model.addAttribute("products", products);
        return "products";
    }

    // http://localhost:8090/products/maxprice - GET
    @RequestMapping("/maxprice")
    public String maxPriceProduct(Model model){
        Product maxProduct = productRepository.findAll (Sort.by("price").descending ()).get(0);
        model.addAttribute("product",
                maxProduct == null ? new Product(): maxProduct);
        return "product";
    }

    // http://localhost:8090/products/minprice - GET
    @RequestMapping("/minprice")
    public String minPriceProduct(Model model){
        Product minProduct = productRepository.findAll (Sort.by("price").ascending()).get(0);
        model.addAttribute("product",
                minProduct == null ? new Product(): minProduct);
        return "product";
    }

    private void checkById(@PathVariable Long id) {
        if(!productRepository.existsById(id)){
            throw new EntityNotFoundException ("Product", id, "Product not found");
        }
    }

    @ExceptionHandler
    public ResponseEntity<EntityNotFoundResponse> handleException(EntityNotFoundException ex){
        EntityNotFoundResponse response = new EntityNotFoundResponse();
        response.setEntityName(ex.getEntityName());
        response.setEntityId(ex.getEntityId());
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
