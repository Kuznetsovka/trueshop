package com.kuznetsovka.trueshop.mapper;

import com.kuznetsovka.trueshop.domain.Category;
import com.kuznetsovka.trueshop.domain.Product;
import com.kuznetsovka.trueshop.dto.ProductDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CategoryMapper.class})
public interface ProductMapper {
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "categories", target = "categories")
    Product toProduct(ProductDto dto);
    List<Product> toProductList(List<ProductDto> products);
    @InheritInverseConfiguration
    ProductDto fromProduct(Product product);
    List<ProductDto> fromProductList(List<Product> products);

}
