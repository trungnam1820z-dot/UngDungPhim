package com.udxp.specification;

import com.udxp.entities.Movie;
import org.springframework.data.jpa.domain.Specification;

public class MovieSpecification {
    public static Specification<Movie> hasTitle(String title) {
        return (root, query, cb) ->
                        title == null ? null :
                        cb.like(root.get("title"), title);
    }
    public static Specification<Movie> hasReleaseDate(Integer releaseDate) {
        return (root, query, cb) ->
                releaseDate == null ? null :
                        cb.like(root.get("year"), releaseDate.toString());
    }
    public static Specification<Movie> hasDirectorName(String directorName) {
        return (root, query, cb) ->
                directorName == null ? null :
                        cb.like(root.get("directorName"), directorName);
    }
    public static Specification<Movie> hasCategoryName(String categoryName) {
        return (root, query, cb) ->
                categoryName == null ? null :
                        cb.like(root.get("categoryName"), categoryName);
    }
    public static Specification<Movie> hasCountryName(String countryName) {
        return (root, query, cb) ->
                countryName == null ? null :
                        cb.like(root.get("countryName"), countryName);
    }
}
