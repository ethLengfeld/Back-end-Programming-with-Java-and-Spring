package org.openclassrooms.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openclassrooms.mediscreen.model.Note;
import org.openclassrooms.mediscreen.repository.NoteRepository;
import org.openclassrooms.mediscreen.service.NoteService;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class NoteServiceTest {

    @Test
    void addOrUpdate() {
        Note note = new Note();
        NoteRepository noteRepository = mock(NoteRepository.class);
        NoteService noteService = new NoteService(noteRepository);
        when(noteRepository.save(note)).thenReturn(note);
        noteService.addOrUpdate(note);
        verify(noteRepository).save(note);
    }

    @Test
    void read() {
        Note note = new Note();
        NoteRepository noteRepository = mock(NoteRepository.class);
        NoteService noteService = new NoteService(noteRepository);
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        Note readNote = noteService.read(1L);
        Assertions.assertNotNull(readNote);

        //negative
        readNote = noteService.read(null);
        Assertions.assertNull(readNote);
    }

    @Test
    void readAll() {
        NoteRepository noteRepository = mock(NoteRepository.class);
        NoteService noteService = new NoteService(noteRepository);
        Assertions.assertNull(noteService.readAll());
    }

    @Test
    void delete() {
        NoteRepository noteRepository = mock(NoteRepository.class);
        NoteService noteService = new NoteService(noteRepository);
        noteService.delete(null);
    }
}
