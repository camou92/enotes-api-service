package com.camoutech.enotesapiservice.repository;

import com.camoutech.enotesapiservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
