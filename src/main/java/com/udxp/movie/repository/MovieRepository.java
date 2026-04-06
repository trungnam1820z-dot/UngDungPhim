package com.udxp.movie.repository;

import com.udxp.movie.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    boolean existsMovieByTitle(String title);
    @EntityGraph(attributePaths = {"categories", "directors", "country"})
    Page<Movie> findAll(Pageable pageable);
    List<Movie> findRecommend(List<Long> watchedMovieIds);
}
