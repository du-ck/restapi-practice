package com.gritstandard.project.biz.repository;

import com.gritstandard.project.biz.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByLoginIdAndPasswordAndGroupNumber(String loginId, String password, String groupNumber);

    Optional<Users> findByLoginId(String userId);
}
