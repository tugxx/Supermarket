package com.minimarket.web_minimarket.mapper;

import com.minimarket.web_minimarket.dto.CategoryRequestDTO;
import com.minimarket.web_minimarket.dto.CategoryResponseDTO;
import com.minimarket.web_minimarket.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "categoryId", source = "categoryId")
    @Mapping(target = "categoryName", source = "categoryName")
    CategoryResponseDTO categoryToCategoryResponse(Category category);

    @Mapping(target = "categoryName", source = "categoryName")
    Category categoryRequestToCategory(CategoryRequestDTO category);
}
