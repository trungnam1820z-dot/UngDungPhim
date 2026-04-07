package com.udxp.masterdata.country.repository;

import com.udxp.masterdata.country.entities.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Page<CountryNameOnly> findAllBy(Pageable pageable);
    Optional<Country> findByCountryName(String countryStr);
}
