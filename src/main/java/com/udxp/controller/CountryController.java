package com.udxp.controller;

import com.udxp.dto.request.CountryCreateRequest;
import com.udxp.dto.response.CountryResponse;
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

    @PostMapping("/create_country")
    public CountryResponse createCountry(@RequestBody CountryCreateRequest request) {
        return countryService.createCountry(request);
    }
    @GetMapping("/countryNames")
    public Page<String> getCountryNames(Pageable pageable) {
        return countryService.getCountryNames(pageable);
    }
    @PutMapping("/{id}")
    public CountryResponse updateCountry(@PathVariable int id, @RequestBody CountryCreateRequest request) {
        return countryService.updateCountry(id,request);
    }
    @DeleteMapping("/countryName")
    public void deleteCountry(@RequestParam("countryName") String countryName) {
        countryService.deleteCountry(countryName);
    }
}
