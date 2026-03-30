package com.udxp.mapper;

import com.udxp.dto.request.CountryCreateRequest;
import com.udxp.dto.response.CountryResponse;
import com.udxp.entities.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    Country toCountry(CountryCreateRequest request);
    CountryResponse toCountryResponse(Country country);
}
