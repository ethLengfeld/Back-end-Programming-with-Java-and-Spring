package org.openclassrooms.mediscreen.repository;

import org.openclassrooms.mediscreen.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note, Long> {}
