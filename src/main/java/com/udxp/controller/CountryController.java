package com.udxp.controller;

import com.udxp.dto.request.CountryCreateRequest;
import com.udxp.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @PostMapping("api/create_country")
    public CountryCreateRequest createCountry(@RequestBody CountryCreateRequest request) {
        return countryService.createCountry(request);
    }
    @GetMapping("api/countryName")
    public Page<String> getCountryNames(Pageable pageable) {
        return countryService.getCountryNames(pageable);
    }
    @PutMapping("api/{id}")
    public CountryCreateRequest updateCountry(@PathVariable int id, @RequestBody CountryCreateRequest request) {
        return countryService.updateCountry(id,request);
    }
    @DeleteMapping("api/countryName")
    public void deleteCountry(@RequestParam("countryName") String countryName) {
        countryService.deleteCountry(countryName);
    }
}
