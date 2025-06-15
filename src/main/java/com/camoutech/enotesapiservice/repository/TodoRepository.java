package com.camoutech.enotesapiservice.repository;

import com.camoutech.enotesapiservice.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findByCreatedBy(Integer userId);
}
