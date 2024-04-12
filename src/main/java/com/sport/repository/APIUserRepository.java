package com.sport.repository;

import com.sport.domain.APIUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface APIUserRepository extends JpaRepository<APIUser, String> {

    @EntityGraph(attributePaths = "roleSet")
    @Query("select u from APIUser u where u.email = :email")
    Optional<APIUser> getWithRoles(@Param("email") String email);

    //닉네임 중복검사
    boolean existsBynickName(String nickName);

    // 이메일 중복검사
    boolean existsByemail(String email);
}
