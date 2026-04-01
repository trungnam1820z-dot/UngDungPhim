package com.udxp.service;

import com.udxp.dto.request.CountryCreateRequest;
import com.udxp.dto.response.CountryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CountryService {
    CountryResponse createCountry(CountryCreateRequest request);
    CountryResponse updateCountry(int id, CountryCreateRequest request);
    void deleteCountry(String countryName);
    Page<String> getCountryNames(Pageable pageable);
}
