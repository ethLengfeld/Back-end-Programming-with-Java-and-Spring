package org.openclassrooms.mediscreen.controller;

import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.constants.GlobalConstants;
import org.openclassrooms.mediscreen.model.Note;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class AssessmentController {

    @GetMapping("/assess")
    public String showAssessment(@RequestParam Long id, Model model) {
        log.info("ASSESSING PATIENT id:{}", id);
        return "assessment";
    }

    @PostMapping("/assess/")
    public String submitAssessment(Long patId, String familyName) {
        log.info("ASSESSING PATIENT");

        return GlobalConstants.HOME;
    }

}
