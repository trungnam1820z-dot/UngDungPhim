package com.udxp.repository;

import com.udxp.entities.Country;
import com.udxp.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>, JpaSpecificationExecutor<Movie> {
    boolean existsMovieByTitle(String title);
}
