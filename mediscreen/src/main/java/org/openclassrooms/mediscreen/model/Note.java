package org.openclassrooms.mediscreen.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "note")
public class Note {

    @Id
    private Long id;
    private List<String> doctorNotes;

    public Note(Long id) {
        this.id = id;
        this.doctorNotes = new ArrayList<>();
    }

    public Note() {
    }
}
