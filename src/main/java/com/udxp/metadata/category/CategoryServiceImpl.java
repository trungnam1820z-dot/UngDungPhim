package com.udxp.metadata.category;

import com.udxp.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Override
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        if(categoryRepository.existsByCategoryName(request.getCategoryName())){
            log.error("Category already exists");
            throw new RuntimeException("Category already exists");
        }
        Category category = categoryMapper.toCategoryEntity(request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    @CacheEvict(value = "categories", key = "#id")
    public CategoryResponse updateCategory(Long id, CategoryCreateRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryMapper.updateCategory(category, request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    @CacheEvict(value = "categories", key = "#categoryName")
    public void deleteCategory(String categoryName) {
        categoryRepository.deleteAll();
    }

    @Override
    @Cacheable(value = "categories", key = "#pageable")
    public Page<String> getCategoryNames(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("countryName").ascending()
        );
        return categoryRepository.findAllBy(sortedPageable)
                .map(CategoryNameOnly::getCategoryName);
    }
}
