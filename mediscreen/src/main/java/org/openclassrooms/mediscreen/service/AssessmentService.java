package org.openclassrooms.mediscreen.service;

import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.constants.GlobalConstants;
import org.openclassrooms.mediscreen.constants.HealthAssessment;
import org.openclassrooms.mediscreen.model.Note;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.util.PatientUtils;
import org.springframework.stereotype.Controller;

import java.util.Locale;

@Slf4j
@Controller
public class AssessmentService {

    public AssessmentService() {}

    public HealthAssessment assessPatient(Patient patient, Note patientNote) {
        char sex = patient.getSex();
        int age = PatientUtils.calculateAge(patient.getDob());

        int countTriggerTerms = 0;
        for (String note : patientNote.getDoctorNotes()) {
            for (String triggerTerm : GlobalConstants.HEALTH_ASSESSMENT_TRIGGER_TERM) {
                if (note.toLowerCase(Locale.ROOT).contains(triggerTerm.toLowerCase(Locale.ROOT))) {
                    countTriggerTerms++;
                }
            }
        }

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
