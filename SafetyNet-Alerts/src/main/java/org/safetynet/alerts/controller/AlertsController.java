package org.safetynet.alerts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AlertsController {

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello there, friend.", HttpStatus.I_AM_A_TEAPOT);
    }

    @GetMapping("/status")
    public ModelAndView alertsStatus() {
        return new ModelAndView("status");
    }

}
