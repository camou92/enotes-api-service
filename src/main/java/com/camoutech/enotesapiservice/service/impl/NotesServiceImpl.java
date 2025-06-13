package com.camoutech.enotesapiservice.service.impl;
import com.camoutech.enotesapiservice.dto.NotesDto;
import com.camoutech.enotesapiservice.dto.NotesDto.CategoryDto;
import com.camoutech.enotesapiservice.entity.FileDetails;
import com.camoutech.enotesapiservice.entity.Notes;
import com.camoutech.enotesapiservice.exception.ResourceNotFoundException;
import com.camoutech.enotesapiservice.repository.CategoryRepository;
import com.camoutech.enotesapiservice.repository.FileRepository;
import com.camoutech.enotesapiservice.repository.NotesRepository;
import com.camoutech.enotesapiservice.service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

        ObjectMapper ob = new ObjectMapper();
        NotesDto notesDto = ob.readValue(notes, NotesDto.class);

        // category validation
        checkCategoryExist(notesDto.getCategory());

        Notes notesMap = mapper.map(notesDto, Notes.class);

        FileDetails fileDtls = saveFileDetails(file);

        if (!ObjectUtils.isEmpty(fileDtls)) {
            notesMap.setFileDetails(fileDtls);
        } else {
            notesMap.setFileDetails(null);
        }

        Notes saveNotes = notesRepository.save(notesMap);
        if (!ObjectUtils.isEmpty(saveNotes)) {
            return true;
        }
        return false;
    }


    private FileDetails saveFileDetails(MultipartFile file) throws IOException {

        if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {

            String originalFilename = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFilename);

            List<String> extensionAllow = Arrays.asList("pdf", "xlsx", "jpg", "png");
            if (!extensionAllow.contains(extension)) {
                throw new IllegalArgumentException("invalid file format ! Upload only .pdf , .xlsx,.jpg");
            }

            String rndString = UUID.randomUUID().toString();
            String uploadfileName = rndString + "." + extension; // sdfsafbhkljsf.pdf

            File saveFile = new File(uploadPath);
            if (!saveFile.exists()) {
                saveFile.mkdir();
            }
            // path : enotesapiservice/notes/java.pdf
            String storePath = uploadPath.concat(uploadfileName);

            // upload file
            long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
            if (upload != 0) {
                FileDetails fileDtls = new FileDetails();
                fileDtls.setOriginalFileName(originalFilename);
                fileDtls.setDisplayFileName(getDisplayName(originalFilename));
                fileDtls.setUploadFileName(uploadfileName);
                fileDtls.setFileSize(file.getSize());
                fileDtls.setPath(storePath);
                FileDetails saveFileDtls = fileRepository.save(fileDtls);
                return saveFileDtls;
            }
        }

        return null;
    }

    private String getDisplayName(String originalFilename) {
        // java_programming_tutorials.pdf

        String extension = FilenameUtils.getExtension(originalFilename);
        String fileName = FilenameUtils.removeExtension(originalFilename);

        if (fileName.length() > 8) {
            fileName = fileName.substring(0, 7);
        }
        fileName = fileName + "." + extension;
        return fileName;
    }

    private void checkCategoryExist(CategoryDto category) throws Exception {
        categoryRepository.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("category id invalid"));
    }


    @Override
    public List<NotesDto> getAllNotes() {
        return notesRepository.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();
    }
}
