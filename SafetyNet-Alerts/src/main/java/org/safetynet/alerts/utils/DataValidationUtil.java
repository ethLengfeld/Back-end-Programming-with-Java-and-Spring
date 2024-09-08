package org.safetynet.alerts.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataValidationUtil {

    public static boolean isAdult(String dob) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            LocalDate birthDate = LocalDate.parse(dob, dtf);
            LocalDate todayDate = LocalDate.now();

            Period timeBetween = Period.between(birthDate, todayDate);
            return timeBetween.getYears() >= 18;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
