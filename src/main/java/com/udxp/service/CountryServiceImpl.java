package com.udxp.service;

import com.udxp.dto.request.CountryCreateRequest;
import com.udxp.entities.Country;
import com.udxp.mapper.CountryMapper;
import com.udxp.repository.CountryNameOnly;
import com.udxp.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public CountryCreateRequest createCountry(CountryCreateRequest request) {
        Country country = countryMapper.toEntity(request);
        return countryMapper.toCreateRequest(countryRepository.save(country));
    }

    @Override
    public CountryCreateRequest updateCountry(int id, CountryCreateRequest request) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found"));
        countryMapper.updateCountry(request, country);
        return countryMapper.toCreateRequest(countryRepository.save(country));
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
