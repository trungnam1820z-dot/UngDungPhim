package com.udxp.repository;

import com.udxp.entities.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    Page<CountryNameOnly> findAllBy(Pageable pageable);

    void deleteByCountryName(String countryName);
}
