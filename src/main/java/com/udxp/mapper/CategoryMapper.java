package com.udxp.mapper;

import com.udxp.dto.request.CategoryCreateRequest;
import com.udxp.dto.response.CategoryResponse;
import com.udxp.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategoryEntity(CategoryCreateRequest request);
    @Mapping(target = "movies", ignore = true)
    CategoryResponse toCategoryResponse(Category category);
    void updateCategory(Category category, @MappingTarget CategoryCreateRequest request);
}
