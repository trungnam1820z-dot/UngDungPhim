package com.udxp.repository;

import com.udxp.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    boolean existsMovieByTitle(String title);
    @Query("""
SELECT DISTINCT m FROM Movie m
LEFT JOIN m.categories c
LEFT JOIN m.directors d
WHERE
    c.id IN :categoryIds
    OR d.id IN :directorIds
    OR m.country.id IN :countryIds
""")
    Page<Movie> recommendMovies(Set<Long> categoryIds, Set<Long> directorIds, Set<Long> countryIds, Pageable pageable);
    Page<Movie> findAllByOrderByReleaseDateDesc(Pageable pageable);
}
