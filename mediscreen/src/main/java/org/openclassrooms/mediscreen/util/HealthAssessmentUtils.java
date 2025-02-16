package org.openclassrooms.mediscreen.util;

import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.constants.GlobalConstants;
import org.openclassrooms.mediscreen.constants.HealthAssessment;
import org.openclassrooms.mediscreen.model.Note;

import java.util.Locale;

@Slf4j
public class HealthAssessmentUtils {

    public static int countTriggerTerms(Note patientNote) {
        int countTriggerTerms = 0;
        for (String note : patientNote.getDoctorNotes()) {
            for (String triggerTerm : GlobalConstants.HEALTH_ASSESSMENT_TRIGGER_TERM) {
                if (note.toLowerCase(Locale.ROOT).contains(triggerTerm.toLowerCase(Locale.ROOT))) {
                    countTriggerTerms++;
                }
            }
        }
        return countTriggerTerms;
    }

    public static HealthAssessment assessPatientHealth(int age, int countTriggerTerms, char sex) {
        if (age > 30) {
            log.info("OVER 30 YR");
            if (countTriggerTerms >= 2 && countTriggerTerms < 6) {
                return HealthAssessment.BORDERLINE;
            }
            else if (countTriggerTerms >= 6 && countTriggerTerms < 8) {
                return HealthAssessment.IN_DANGER;
            }
            else if (countTriggerTerms >= 8) {
                return HealthAssessment.EARLY_ONSET;
            }
        }
        else {
            if (sex == 'M') {
                log.info("UNDER 30 YR MALE");
                if (countTriggerTerms >= 3 && countTriggerTerms < 5) {
                    return HealthAssessment.IN_DANGER;
                }
                else if (countTriggerTerms >= 5) {
                    return HealthAssessment.EARLY_ONSET;
                }
            }
            else {
                log.info("UNDER 30 YR FEMALE");
                if (countTriggerTerms >= 4 && countTriggerTerms < 7) {
                    return HealthAssessment.IN_DANGER;
                }
                else if (countTriggerTerms >= 7) {
                    return HealthAssessment.EARLY_ONSET;
                }
            }
        }
        return HealthAssessment.NONE;
    }
}
