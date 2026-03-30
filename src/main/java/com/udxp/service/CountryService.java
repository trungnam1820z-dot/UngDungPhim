package com.udxp.service;

import com.udxp.dto.request.CountryCreateRequest;
import com.udxp.dto.response.CountryResponse;
import com.udxp.entities.Country;
import com.udxp.mapper.CountryMapper;
import com.udxp.repository.CountryRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public Country createCountry(CountryCreateRequest request) {
        if (countryRepository.existsByCountryCode(request.getCountryCode()))
            throw new EntityExistsException("Country already exists");
        Country country = countryMapper.toCountry(request);
        return countryRepository.save(country);
    }
}