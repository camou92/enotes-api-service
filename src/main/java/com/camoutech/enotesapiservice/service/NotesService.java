package com.camoutech.enotesapiservice.service;

import com.camoutech.enotesapiservice.dto.NotesDto;
import com.camoutech.enotesapiservice.entity.FileDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {
    Boolean saveNotes(String notes, MultipartFile file) throws Exception;

    public List<NotesDto> getAllNotes();

    public byte[] downloadFile(FileDetails fileDetails) throws Exception;

    public FileDetails getFileDetails(Integer id) throws Exception;

    public List<NotesDto> getAllNotesByUser(Integer userId);
}
