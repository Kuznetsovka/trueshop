package com.kuznetsovka.trueshop.controller;

import com.kuznetsovka.trueshop.dao.ProductRepository;
import com.kuznetsovka.trueshop.domain.Product;
import com.kuznetsovka.trueshop.dto.BucketDto;
import com.kuznetsovka.trueshop.service.bucket.BucketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.security.Principal;

@Controller
public class BucketController {

    private final BucketService bucketService;
    private final ProductRepository productRepository;

    public BucketController(BucketService bucketService, ProductRepository productRepository) {
        this.bucketService = bucketService;
        this.productRepository = productRepository;
    }

    @GetMapping("/bucket")
    public String aboutBucket(Model model, Principal principal){
        if(principal == null){
            model.addAttribute("bucket", new BucketDto ());
        }
        else {
            BucketDto bucketDto = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket", bucketDto);
        }

        return "bucket";
    }

    @PostMapping("/bucket")
    public String commitBucket(Model model, Principal principal){
        if(principal != null){
            bucketService.commitBucketToOrder(principal.getName());
        }
        return "redirect:/bucket";
    }

    @RequestMapping("/bucket/product/{id}")
    public String deleteProductFromBucket(Model model, Principal principal,@PathVariable Long id){
        if(principal != null){
            Product byId = productRepository.findAllById (id).get(0);
            bucketService.deleteProductFromBucket (principal.getName(),byId);
        }
        return "redirect:/bucket";
    }

}
