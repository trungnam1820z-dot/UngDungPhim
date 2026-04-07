package com.udxp.mapper;

import com.udxp.masterdata.director.dto.request.DirectorCreateRequest;
import com.udxp.masterdata.director.dto.response.DirectorResponse;
import com.udxp.masterdata.director.entities.Director;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DirectorMapper {
    Director toDirectorEntity(DirectorCreateRequest request);
    @Mapping(source = "country.countryName", target = "countryName")
    @Mapping(source = "directorName", target = "directorName")
    DirectorResponse toDirectorResponse(Director director);
    @Mapping(target = "movies", ignore = true)
    void updateDirector(DirectorCreateRequest request, @MappingTarget Director director);
}
