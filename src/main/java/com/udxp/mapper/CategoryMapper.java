package com.udxp.mapper;

import com.udxp.masterdata.category.dto.resquest.CategoryCreateRequest;
import com.udxp.masterdata.category.dto.response.CategoryResponse;
import com.udxp.masterdata.category.entities.Category;
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
