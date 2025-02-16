package org.openclassrooms.util;

import org.junit.jupiter.api.Test;
import org.openclassrooms.mediscreen.constants.HealthAssessment;
import org.openclassrooms.mediscreen.model.Note;
import org.openclassrooms.mediscreen.util.TemplateUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateUtilsTest {

    @Test
    void highlightTriggerTerms() {
        String doctorNote = "patient's body height is on track";
        List<String> doctorNotes = List.of(doctorNote);
        Note note = new Note();
        note.setDoctorNotes(doctorNotes);

        List<String> highlightedTerms = TemplateUtils.highlightTriggerTerms(note);
        assertEquals("patient's <span class='highlight'>Body Height</span> is on track", highlightedTerms.get(0));
    }

    @Test
    void colorCodeHealthAssessment() {
        assertEquals("<span class='noneAssessment'>None</span>",
                TemplateUtils.colorCodeHealthAssessment(HealthAssessment.NONE));
        assertEquals("<span class='borderlineAssessment'>Borderline</span>",
                TemplateUtils.colorCodeHealthAssessment(HealthAssessment.BORDERLINE));
        assertEquals("<span class='inDangerAssessment'>In Danger</span>",
                TemplateUtils.colorCodeHealthAssessment(HealthAssessment.IN_DANGER));
        assertEquals("<span class='earlyOnsetAssessment'>Early Onset</span>",
                TemplateUtils.colorCodeHealthAssessment(HealthAssessment.EARLY_ONSET));
    }
}
