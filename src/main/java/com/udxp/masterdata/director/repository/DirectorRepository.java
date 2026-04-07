package com.udxp.masterdata.director.repository;

import com.udxp.masterdata.director.entities.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    Page<DirectorNameOnly> findAllBy(Pageable pageable);

    Optional<Director> findByDirectorName(String directorName);
}
