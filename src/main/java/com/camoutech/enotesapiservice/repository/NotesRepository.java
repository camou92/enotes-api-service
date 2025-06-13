package com.camoutech.enotesapiservice.repository;

import com.camoutech.enotesapiservice.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, Integer> {
}
