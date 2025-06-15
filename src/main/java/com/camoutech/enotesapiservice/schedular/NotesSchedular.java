package com.camoutech.enotesapiservice.schedular;

import com.camoutech.enotesapiservice.entity.Notes;
import com.camoutech.enotesapiservice.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotesSchedular {

    @Autowired
    private NotesRepository notesRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    // @Scheduled(cron = "* * * ? * *")
    public void deleteNotesSchedular() {
        // 20-nov -14 nov -7days
        LocalDateTime cutOffDate = LocalDateTime.now().minusDays(7);
        List<Notes> deleteNotes = notesRepository.findAllByIsDeletedAndDeletedOnBefore(true, cutOffDate);
        notesRepository.deleteAll(deleteNotes);
    }
}
