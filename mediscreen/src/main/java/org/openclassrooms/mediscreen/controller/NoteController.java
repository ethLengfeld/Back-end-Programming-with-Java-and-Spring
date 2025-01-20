package org.openclassrooms.mediscreen.controller;

import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.model.Note;
import org.openclassrooms.mediscreen.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@Controller
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/patHistory")
    public String showNoteForm(@RequestParam Long id, Model model) {
        log.info("NOTE FORM PAGE");
        log.info("id: {}", id);

        Note note;
        try {
            note = noteService.readNote(id);
        } catch (NoSuchElementException e) {
            log.error("Error retrieving notes for patient id");
            note = new Note(id);
        }

        model.addAttribute("note", Objects.requireNonNullElseGet(note, () -> new Note(id)));

        return "noteForm";
    }

    @PostMapping("/patHistory/add")
    public String submitNoteForm(Long patId, String note) {
        log.info("SUBMITTING NOTE");
        log.info("patId {}, note\n{}", patId, note);

        // TODO move into service
        Note newNote;
        List<String> doctorNotes;
        try {
            newNote = noteService.readNote(patId);
            // TODO throw exception if newNote is null
            doctorNotes = newNote.getDoctorNotes();
        } catch (NoSuchElementException e) {
            log.error("Error saving notes for patId");
            newNote = new Note(patId);
            doctorNotes = new ArrayList<>();

        }
        doctorNotes.add(note);
        newNote.setDoctorNotes(doctorNotes);

        noteService.addOrUpdateNote(newNote);
        return "redirect:/";
    }

}
