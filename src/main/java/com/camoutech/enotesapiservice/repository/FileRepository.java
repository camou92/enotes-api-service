package com.camoutech.enotesapiservice.repository;

import com.camoutech.enotesapiservice.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileDetails, Integer> {
}
