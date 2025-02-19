package org.openclassrooms.util;

import org.junit.jupiter.api.Test;
import org.openclassrooms.mediscreen.constants.HealthAssessment;
import org.openclassrooms.mediscreen.model.Note;
import org.openclassrooms.mediscreen.util.HealthAssessmentUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthAssessmentUtilsTest {

    @Test
    void countTriggerTerms() {
        Note note = new Note();
        note.setDoctorNotes(List.of("body weight", "this is a note"));
        assertEquals(1, HealthAssessmentUtils.countTriggerTerms(note));

        note.setDoctorNotes(null);
        assertEquals(0, HealthAssessmentUtils.countTriggerTerms(note));

    }

    @Test
    void assessPatientHealth() {
        assertEquals(HealthAssessment.NONE,
                HealthAssessmentUtils.assessPatientHealth(40,
                        1, 'M'));
        assertEquals(HealthAssessment.BORDERLINE,
                HealthAssessmentUtils.assessPatientHealth(40,
                        3, 'M'));
        assertEquals(HealthAssessment.IN_DANGER,
                HealthAssessmentUtils.assessPatientHealth(40,
                        7, 'M'));
        assertEquals(HealthAssessment.EARLY_ONSET,
                HealthAssessmentUtils.assessPatientHealth(40,
                        9, 'M'));

        assertEquals(HealthAssessment.NONE,
                HealthAssessmentUtils.assessPatientHealth(29,
                        1, 'M'));
        assertEquals(HealthAssessment.IN_DANGER,
                HealthAssessmentUtils.assessPatientHealth(29,
                        4, 'M'));
        assertEquals(HealthAssessment.EARLY_ONSET,
                HealthAssessmentUtils.assessPatientHealth(29,
                        6, 'M'));

        assertEquals(HealthAssessment.NONE,
                HealthAssessmentUtils.assessPatientHealth(29,
                        1, 'F'));
        assertEquals(HealthAssessment.IN_DANGER,
                HealthAssessmentUtils.assessPatientHealth(29,
                        5, 'F'));
        assertEquals(HealthAssessment.EARLY_ONSET,
                HealthAssessmentUtils.assessPatientHealth(29,
                        9, 'F'));
    }
}
