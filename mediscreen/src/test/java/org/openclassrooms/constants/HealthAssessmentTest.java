package org.openclassrooms.constants;

import org.junit.jupiter.api.Test;
import org.openclassrooms.mediscreen.constants.HealthAssessment;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthAssessmentTest {

    @Test
    void healthAssessmentEnums() {
        HealthAssessment healthAssessment = HealthAssessment.NONE;
        assertEquals("None", healthAssessment.getValue());
        healthAssessment = HealthAssessment.BORDERLINE;
        assertEquals("Borderline", healthAssessment.getValue());
        healthAssessment = HealthAssessment.IN_DANGER;
        assertEquals("In Danger", healthAssessment.getValue());
        healthAssessment = HealthAssessment.EARLY_ONSET;
        assertEquals("Early Onset", healthAssessment.getValue());
    }
}
