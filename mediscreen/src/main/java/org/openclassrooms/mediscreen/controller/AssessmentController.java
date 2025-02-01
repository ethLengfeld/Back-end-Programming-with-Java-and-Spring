package org.openclassrooms.mediscreen.controller;

import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.constants.GlobalConstants;
import org.openclassrooms.mediscreen.model.Note;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Controller
public class AssessmentController {

    @GetMapping("/assess")
    public String showAssessment(Model model) {
        return null;
    }

    @PostMapping("/assess/")
    public String submitAssessment(Long patId, String note) {
        log.info("SUBMITTING NOTE");

        return GlobalConstants.HOME;
    }

}
