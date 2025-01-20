package org.openclassrooms.mediscreen.service;

import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.model.Note;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NoteService {

    private NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void addOrUpdateNote(Note note) {
        log.info("SAVING NOTE");
        noteRepository.save(note);
    }

    public Note readNote(Long id) {
        log.info("READING NOTE WITH id:::{}", id);
        if (id == null) {
            return null;
        }
        return noteRepository.findById(id).get();
    }

    public List<Note> readNotes() {
        log.info("READING ALL NOTES");
        return noteRepository.findAll();
    }
}
