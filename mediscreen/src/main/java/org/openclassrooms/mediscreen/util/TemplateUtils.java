package org.openclassrooms.mediscreen.util;

import org.openclassrooms.mediscreen.constants.GlobalConstants;
import org.openclassrooms.mediscreen.constants.HealthAssessment;
import org.openclassrooms.mediscreen.model.Note;

import java.util.ArrayList;
import java.util.List;

public class TemplateUtils {

    public static List<String> highlightTriggerTerms(Note note) {
        if (note.getDoctorNotes() == null) {
            return new ArrayList<>();
        }

        List<String> highlightedNotes = new ArrayList<>();
        for (String doctorNote : note.getDoctorNotes()) {
            for (String triggerTerm : GlobalConstants.HEALTH_ASSESSMENT_TRIGGER_TERM) {
                doctorNote = doctorNote.replaceAll("(?i)\\b" + triggerTerm + "\\b",
                        "<span class='highlight'>" + triggerTerm + "</span>");
            }
            highlightedNotes.add(doctorNote);
        }
        return highlightedNotes;
    }

    public static String colorCodeHealthAssessment(HealthAssessment healthAssessment) {
        return switch (healthAssessment) {
            case NONE -> "<span class='noneAssessment'>" + healthAssessment.getValue() + "</span>";
            case IN_DANGER -> "<span class='inDangerAssessment'>" + healthAssessment.getValue() + "</span>";
            case BORDERLINE -> "<span class='borderlineAssessment'>" + healthAssessment.getValue() + "</span>";
            case EARLY_ONSET -> "<span class='earlyOnsetAssessment'>" + healthAssessment.getValue() + "</span>";
        };
    }
}
