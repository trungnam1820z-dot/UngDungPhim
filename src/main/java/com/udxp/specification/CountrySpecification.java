package com.udxp.specification;

import com.udxp.entities.Country;
import com.udxp.entities.Movie;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class CountrySpecification {
    public static Specification<Movie> hasCountryName(String countryName) {
        return (root, query, cb) -> {
            if (countryName == null) return null;
            query.distinct(true);
            Join< Movie, Country> countryJoin = root.join("country");
            return cb.equal(countryJoin.get("countryName"), countryName);
        };
    }
}
