package com.kuznetsovka.trueshop.dao;

import com.kuznetsovka.trueshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByTitle(String title);
    List<Product> findAllById(Long id);
    List<Product> findAllByIdBetween(Long startId, Long endId);
    List<Product> findAllByPriceBetween(Double priceFrom, Double priceTo);
    List<Product> findAllByTitleLike(String title);
    @Override
    void deleteById(Long aLong);
}
