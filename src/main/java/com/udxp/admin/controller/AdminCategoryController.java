package com.udxp.admin.controller;

import com.udxp.masterdata.category.dto.response.CategoryResponse;
import com.udxp.masterdata.category.dto.resquest.CategoryCreateRequest;
import com.udxp.masterdata.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class AdminCategoryController {
    private final CategoryService categoryService;
    @PostMapping("/create_category")
    public CategoryResponse createCategory(@RequestBody CategoryCreateRequest request) {
        return categoryService.createCategory(request);
    }
    @GetMapping()
    public Page<String> getCategoryNames(Pageable pageable) {
        return categoryService.getCategoryNames(pageable);
    }
    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Long id, @RequestBody CategoryCreateRequest request) {
        return categoryService.updateCategory(id,request);
    }
    @DeleteMapping("/{categoryName}")
    public void deleteCategory(@PathVariable String categoryName) {
        categoryService.deleteCategory(categoryName);
    }
}
