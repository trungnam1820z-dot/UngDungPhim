package com.udxp.metadata.country;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CountryService {
    Page<String> getCountryNames(Pageable pageable);
}
