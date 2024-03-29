package com.sport.repository;

import com.sport.domain.APIUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface APIUserRepository extends JpaRepository<APIUser, String> {

    @EntityGraph(attributePaths = "roleSet")
    @Query("select u from APIUser u where u.email = :email")
    Optional<APIUser> getWithRoles(String email);

}
