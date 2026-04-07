package com.udxp.masterdata.category.repository;

import com.udxp.masterdata.category.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryName(String categoryName);
    Page<CategoryNameOnly> findAllBy(Pageable pageable);

    Optional<Category> findByCategoryName(String categoryName);
}
