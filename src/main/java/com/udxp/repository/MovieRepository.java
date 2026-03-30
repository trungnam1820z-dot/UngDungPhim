package com.udxp.repository;

import com.udxp.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByDirectors_directorNameContainingIgnoreCase(String directorName);
    List<Movie> findByCategories_categoryNameContainingIgnoreCase(String categoryName);
    List<Movie> findMoviesByCountry_countryNameContainingIgnoreCase(String countryName);
    Optional<Movie> findMovieByTitle(String title);
    boolean existsMovieByTitle(String title);
}
