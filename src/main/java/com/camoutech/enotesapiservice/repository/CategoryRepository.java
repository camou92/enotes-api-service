package com.camoutech.enotesapiservice.repository;

import com.camoutech.enotesapiservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
