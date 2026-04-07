package com.udxp.admin.controller;

import com.udxp.masterdata.country.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/country")
public class AdminCountryController {
    private final CountryService countryService;
    @GetMapping("/countryNames")
    public Page<String> getCountryNames(Pageable pageable) {
        return countryService.getCountryNames(pageable);
    }
}
