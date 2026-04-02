package com.udxp.service;

import com.udxp.dto.request.DirectorCreateRequest;
import com.udxp.dto.response.DirectorResponse;
import com.udxp.entities.Country;
import com.udxp.entities.Director;
import com.udxp.mapper.DirectorMapper;
import com.udxp.repository.CountryRepository;
import com.udxp.repository.DirectorNameOnly;
import com.udxp.repository.DirectorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class DirectorServiceImpl implements DirectorService{
    private final DirectorRepository directorRepository;
    private final DirectorMapper directorMapper;
    private final CountryRepository countryRepository;
    @Override
    public DirectorResponse createDirector(DirectorCreateRequest request) {
        Director director = directorMapper.toDirectorEntity(request);
        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found"));
        director.setCountry(country);
        Director director1 = directorRepository.save(director);
        return  directorMapper.toDirectorResponse(directorRepository.save(director1));
    }

    @Override
    @CacheEvict(value = "directors", key = "#id")
    public DirectorResponse updateDirector(int id, DirectorCreateRequest request) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Director not found"));
        directorMapper.updateDirector(request, director);
        return directorMapper.toDirectorResponse(directorRepository.save(director));
    }

    @Override
    @CacheEvict(value = "directors", key = "#id")
    public void deleteDirector(int id) {
        directorRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = "directors", key = "#pageable")
    public Page<String> getDirector(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("directorName").ascending()
        );
        return directorRepository.findAllBy(sortedPageable)
                .map(DirectorNameOnly::getDirectorName);
    }
}
