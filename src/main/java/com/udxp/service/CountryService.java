package com.udxp.service;

import com.udxp.dto.request.CountryCreateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CountryService {
    CountryCreateRequest createCountry(CountryCreateRequest request);
    CountryCreateRequest updateCountry(int id, CountryCreateRequest request);
    void deleteCountry(String countryName);
    Page<String> getCountryNames(Pageable pageable);
}
