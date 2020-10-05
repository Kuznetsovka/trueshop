package com.kuznetsovka.trueshop.mapper;

import com.kuznetsovka.trueshop.domain.Category;
import com.kuznetsovka.trueshop.dto.CategoryDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface CategoryMapper {
    CategoryMapper MAPPER = Mappers.getMapper(CategoryMapper.class);
    Category toCategory(CategoryDto dto);
    List<Category> toCategoryList(List<CategoryDto> categories);
    @InheritInverseConfiguration
    CategoryDto fromCategory(Category category);
    List<CategoryDto> fromCategoryList(List<Category> categories);
}
