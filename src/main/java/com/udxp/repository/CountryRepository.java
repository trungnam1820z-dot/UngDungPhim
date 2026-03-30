package com.udxp.repository;

import com.udxp.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    boolean existsByCountryCode(String countryCode);

    boolean findCountriesByCountryCode(String countryCode);
}
