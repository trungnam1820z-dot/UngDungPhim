package com.udxp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CountryService {
    Page<String> getCountryNames(Pageable pageable);
}
