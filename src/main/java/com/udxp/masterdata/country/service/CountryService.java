package com.udxp.masterdata.country.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CountryService {
    Page<String> getCountryNames(Pageable pageable);
}
