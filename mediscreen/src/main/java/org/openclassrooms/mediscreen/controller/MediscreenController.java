package org.openclassrooms.mediscreen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MediscreenController
{
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("name", "John Doe");
        model.addAttribute("medications",
                List.of("Aspirin", "Saline Spray", "Cough Syrup"));
        return "index";
    }
}
