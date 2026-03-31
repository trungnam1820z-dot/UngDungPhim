package com.udxp.mapper;

import com.udxp.dto.request.CountryCreateRequest;
import com.udxp.dto.response.CountryResponse;
import com.udxp.entities.Country;
import com.udxp.entities.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = MovieMapper.class)
public interface CountryMapper {
    @Mapping(target = "movies", source = "movies")
    CountryResponse toResponse(Country country, List<Movie> movies);
    CountryCreateRequest toCreateRequest(Country country);
    Country toEntity(CountryCreateRequest request);
    void updateCountry(CountryCreateRequest request, @MappingTarget Country country);
}
