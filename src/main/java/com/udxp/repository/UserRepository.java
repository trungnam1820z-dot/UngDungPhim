package com.udxp.repository;

import com.udxp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsUsersByUserName(String userName);

    Page<User> findAll(Specification<User> spec, Pageable sortedPageable);
}
