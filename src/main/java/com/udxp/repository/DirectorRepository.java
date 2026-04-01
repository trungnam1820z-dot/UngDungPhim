package com.udxp.repository;

import com.udxp.entities.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer> {
    Page<DirectorNameOnly> findAllBy(Pageable pageable);
    boolean existsByDirectorName(String directorName);
}
