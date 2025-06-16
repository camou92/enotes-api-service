package com.camoutech.enotesapiservice.repository;

import com.camoutech.enotesapiservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Boolean existsByEmail(String email);
}
