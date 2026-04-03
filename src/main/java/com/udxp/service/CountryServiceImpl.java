package com.udxp.service;

import com.udxp.repository.CountryNameOnly;
import com.udxp.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public Page<String> getCountryNames(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("countryName").ascending()
        );
        return countryRepository.findAllBy(sortedPageable)
                .map(CountryNameOnly::getCountryName);
    }
}
