package com.udxp.service;

import com.udxp.dto.request.CategoryCreateRequest;
import com.udxp.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryResponse createCategory(CategoryCreateRequest request);
    CategoryResponse updateCategory(int id, CategoryCreateRequest request);
    void deleteCategory(String categoryName);
    Page<String> getCategoryNames(Pageable pageable);
}
