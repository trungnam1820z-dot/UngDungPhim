package com.udxp.service;

import com.udxp.dto.request.CountryCreateRequest;
import com.udxp.dto.response.CountryResponse;
import com.udxp.entities.Country;
import com.udxp.mapper.CountryMapper;
import com.udxp.repository.CountryNameOnly;
import com.udxp.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public CountryResponse createCountry(CountryCreateRequest request) {
        if(countryRepository.existsByCountryName(request.getCountryName())){
            log.error("Country already exists");
            throw new RuntimeException("Country already exists");
        }
        Country country = countryMapper.toCountry(request);
        return countryMapper.toResponse(countryRepository.save(country));
    }

    @Override
    public CountryResponse updateCountry(int id, CountryCreateRequest request) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found"));
        countryMapper.updateCountry(request, country);
        return countryMapper.toResponse(countryRepository.save(country));
    }
    @Override
    public void deleteCountry(String countryName) {
        countryRepository.deleteByCountryName(countryName);
    }
    @Override
    public Page<String> getCountryNames(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("countryName").ascending()
        );
        return countryRepository.findAllBy(sortedPageable)
                .map(CountryNameOnly::getCountryName);
    }
}
