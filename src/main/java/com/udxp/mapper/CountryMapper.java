package com.udxp.mapper;

import com.udxp.metadata.country.CountryCreateRequest;
import com.udxp.metadata.country.CountryResponse;
import com.udxp.metadata.country.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = MovieMapper.class)
public interface CountryMapper {
    @Mapping(target = "movies", ignore = true)
    CountryResponse toResponse(Country country);
    Country toCountry(CountryCreateRequest request);
    void updateCountry(CountryCreateRequest request, @MappingTarget Country country);
}
