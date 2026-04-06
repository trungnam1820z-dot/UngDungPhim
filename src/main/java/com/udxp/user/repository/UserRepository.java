package com.udxp.user.repository;

import com.udxp.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUsersByUserName(String userName);

    Optional<User> findByUserName(String userName);
}
