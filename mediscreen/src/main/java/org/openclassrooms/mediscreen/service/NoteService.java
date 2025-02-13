package org.openclassrooms.mediscreen.service;

import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.model.Note;
import org.openclassrooms.mediscreen.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NoteService implements CrudService<Note> {

    private NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public void addOrUpdate(Note note) {
        log.info("SAVING NOTE");
        noteRepository.save(note);
    }

    @Override
    public Note read(Long id) {
        log.info("READING NOTE WITH id:::{}", id);
        if (id == null) {
            return null;
        }
        //TODO fix no notes returned id=10
        return noteRepository.findById(id).get();
    }

    @Override
    public List<Note> readAll() {
        log.info("noop");
        return null;
//        return noteRepository.findAll();
    }

    @Override
    public void delete(Note note) {
        // noop
//        noteRepository.delete(note);
    }
}
