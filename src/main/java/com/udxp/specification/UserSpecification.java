package com.udxp.specification;

import com.udxp.entities.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class UserSpecification {
    public static Specification<User> createdAfter(LocalDate fromDate) {
        return (root, query, cb) ->
                fromDate == null ? null :
                        cb.greaterThanOrEqualTo(root.get("createAt"), fromDate);
    }

    public static Specification<User> createdBefore(LocalDate toDate) {
        return (root, query, cb) ->
                toDate == null ? null :
                        cb.lessThanOrEqualTo(root.get("createAt"), toDate);
    }
}
