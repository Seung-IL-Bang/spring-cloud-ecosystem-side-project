package com.project.user_service.repository;

import com.project.user_service.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUserId(String userId);

    Optional<Users> findByEmail(String email);

}
