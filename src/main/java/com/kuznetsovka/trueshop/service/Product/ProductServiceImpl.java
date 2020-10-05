package com.kuznetsovka.trueshop.service.Product;

import com.kuznetsovka.trueshop.dao.ProductRepository;
import com.kuznetsovka.trueshop.domain.Product;
import com.kuznetsovka.trueshop.dto.ProductDto;
import com.kuznetsovka.trueshop.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @PersistenceContext
    private final EntityManager em;
    private final ProductMapper mapper = ProductMapper.MAPPER;

    private final ProductRepository dao;


    public ProductServiceImpl(EntityManager em, ProductRepository dao) {
        this.em = em;
        this.dao = dao;
    }

    @Override
    public ProductDto findById(Long id) {
        return mapper.fromProduct(dao.getOne(id));
    }

    @Override
    public Product getById(Long id) {
        return dao.findById(id).orElse(null);
    }

    @Transactional
    public void saveAndSet(Product product){
        dao.save(product);
    }

    @Override
    public List<ProductDto> findAll() {
        return mapper.fromProductList(dao.findAll());
    }

    @Transactional
    @Override
    public ProductDto save(ProductDto dto) {
        Product entity = mapper.toProduct(dto);
        Product savedEntity = dao.save(entity);
        return mapper.fromProduct(savedEntity);
    }

    @Transactional
    public void delete(Long id){
        dao.deleteById (id);
    }

}
