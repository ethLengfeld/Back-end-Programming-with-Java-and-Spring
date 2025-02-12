package org.openclassrooms.mediscreen.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PatientUtils {

    public static int calculateAge(String dob) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate birthDate = LocalDate.parse(dob, dtf);
            LocalDate todayDate = LocalDate.now();

            Period timeBetween = Period.between(birthDate, todayDate);
            return timeBetween.getYears();
        } catch (DateTimeParseException e) {
            return -1;
        }
    }
}
