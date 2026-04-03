package com.udxp.controller;

import com.udxp.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/country")
public class CountryController {
    private final CountryService countryService;
    @GetMapping("/countryNames")
    public Page<String> getCountryNames(Pageable pageable) {
        return countryService.getCountryNames(pageable);
    }
}
