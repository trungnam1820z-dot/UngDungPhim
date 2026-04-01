package com.udxp.mapper;

import com.udxp.dto.request.MovieCreateRequest;
import com.udxp.dto.response.MovieResponse;
import com.udxp.entities.Category;
import com.udxp.entities.Director;
import com.udxp.entities.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "directors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Movie toMovie(MovieCreateRequest request);

    // entity -> response
    @Mapping( source = "country.countryName" ,target = "countryName")
    @Mapping(target = "directorName", source = "directors")
    @Mapping(target = "categoryName", source = "categories")
    MovieResponse toResponse(Movie movie);

    @Mapping(target = "country", ignore = true)
    @Mapping(target = "directors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateMovie(@MappingTarget Movie movie, MovieCreateRequest request);

    // Custom object -> String
    default List<String> mapDirectors(List<Director> directors) {
        if (directors == null) return List.of();
        return directors.stream()
                .map(Director::getDirectorName)
                .toList();
    }
    default List<String> mapCategories(List<Category> categories) {
        if (categories == null) return List.of();
        return categories.stream()
                .map(Category::getCategoryName)
                .toList();
    }
}
