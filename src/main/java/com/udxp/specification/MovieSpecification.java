package com.udxp.specification;

import com.udxp.entities.Movie;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class MovieSpecification {

    public static Specification<Movie> hasTitle(String title) {
        return (root, query, cb) ->
                (title == null || title.isBlank()) ? null :
                        cb.like(root.get("title"), "%" + title + "%");
    }
    public static Specification<Movie> hasReleaseDate(Integer releaseDate) {
        return (root, query, cb) ->
                releaseDate == null ? null :
                        cb.equal(root.get("year"), releaseDate);
    }
    public static Specification<Movie> hasDirectorName(String directorName) {
        return (root, query, cb) -> {
            if (directorName == null || directorName.isBlank()) return null;
            query.distinct(true);
            Join<Object, Object> join = root.join("directors");
            return cb.like(join.get("directorName"), "%" + directorName + "%");
        };
    }
    public static Specification<Movie> hasCategoryName(String categoryName) {
        return (root, query, cb) -> {
            if (categoryName == null || categoryName.isBlank()) return null;

            query.distinct(true);
            Join<Object, Object> join = root.join("categories");
            return cb.like(join.get("categoryName"), "%" + categoryName + "%");
        };
    }
    public static Specification<Movie> hasCountryName(String countryName) {
        return (root, query, cb) -> {
            if (countryName == null || countryName.isBlank()) return null;
            query.distinct(true);
            Join<Object, Object> join = root.join("country");
            return cb.like(join.get("countryName"), "%" + countryName + "%");
        };
    }
}
