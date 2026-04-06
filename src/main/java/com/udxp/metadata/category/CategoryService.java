package com.udxp.metadata.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryResponse createCategory(CategoryCreateRequest request);
    CategoryResponse updateCategory(Long id, CategoryCreateRequest request);
    void deleteCategory(String categoryName);
    Page<String> getCategoryNames(Pageable pageable);
}
