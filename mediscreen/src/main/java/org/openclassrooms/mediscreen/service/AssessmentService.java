package org.openclassrooms.mediscreen.service;

import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.constants.HealthAssessment;
import org.openclassrooms.mediscreen.model.Note;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.util.HealthAssessmentUtils;
import org.openclassrooms.mediscreen.util.PatientUtils;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class AssessmentService {

    public AssessmentService() {}

    public HealthAssessment assessPatient(Patient patient, Note patientNote) {
        char sex = patient.getSex();
        int age = PatientUtils.calculateAge(patient.getDob());
        int countTriggerTerms = HealthAssessmentUtils.countTriggerTerms(patientNote);

        return HealthAssessmentUtils.assessPatientHealth(age, countTriggerTerms, sex);
    }
}
