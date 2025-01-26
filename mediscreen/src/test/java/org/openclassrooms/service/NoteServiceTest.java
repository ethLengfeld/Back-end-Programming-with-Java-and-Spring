package org.openclassrooms.service;

import org.junit.jupiter.api.Test;
import org.openclassrooms.mediscreen.model.Note;
import org.openclassrooms.mediscreen.repository.NoteRepository;
import org.openclassrooms.mediscreen.service.NoteService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NoteServiceTest {
    //TODO tests
    @Test
    void addOrUpdate() {
        Note note = new Note();
        NoteRepository noteRepository = mock(NoteRepository.class);
        NoteService noteService = new NoteService(noteRepository);
        when(noteRepository.save(note)).thenReturn(note);

        noteService.addOrUpdate(note);
    }
}
