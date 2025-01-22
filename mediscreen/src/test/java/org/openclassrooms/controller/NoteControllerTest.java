package org.openclassrooms.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openclassrooms.mediscreen.controller.NoteController;
import org.openclassrooms.mediscreen.model.Note;
import org.openclassrooms.mediscreen.service.NoteService;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NoteControllerTest {

    private static Note sampleNote;

    @BeforeAll
    static void setUp() {
        sampleNote = new Note(1L);
        List<String> doctorNotes = new ArrayList<>(List.of("first doctor note", "this is the latest dr note"));
        sampleNote.setDoctorNotes(doctorNotes);
    }

    @Test
    void showNoteForm() {
        Model model = mock(Model.class);
        NoteService noteService = mock(NoteService.class);
        NoteController noteController = new NoteController(noteService);
        //existing note
        when(noteService.read(1L)).thenReturn(sampleNote);
        String value = noteController.showNoteForm(1L, model);
        Assertions.assertEquals("noteForm", value);

        //new note
        when(noteService.read(2L)).thenThrow(NoSuchElementException.class);
        value = noteController.showNoteForm(2L, model);
        Assertions.assertEquals("noteForm", value);
    }

    @Test
    void submitNoteForm() {
        Model model = mock(Model.class);
        NoteService noteService = mock(NoteService.class);
        NoteController noteController = new NoteController(noteService);
        //add doctor note to existing note
        when(noteService.read(1L)).thenReturn(sampleNote);
        String value = noteController.submitNoteForm(1L, "this is the newest note");
        Assertions.assertEquals("redirect:/", value);
        Assertions.assertEquals(3, sampleNote.getDoctorNotes().size());

        //new note
        when(noteService.read(2L)).thenThrow(NoSuchElementException.class);
        value = noteController.submitNoteForm(2L, "this is the newest note");
        Assertions.assertEquals("redirect:/", value);
    }
}
