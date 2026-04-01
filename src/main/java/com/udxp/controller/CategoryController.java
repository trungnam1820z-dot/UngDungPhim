package com.udxp.controller;

import com.udxp.dto.request.CategoryCreateRequest;
import com.udxp.dto.response.CategoryResponse;
import com.udxp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
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
    public CategoryResponse updateCategory(@PathVariable int id, @RequestBody CategoryCreateRequest request) {
        return categoryService.updateCategory(id,request);
    }
    @DeleteMapping("/{categoryName}")
    public void deleteCountry(@PathVariable String categoryName) {
        categoryService.deleteCategory(categoryName);
    }
}
