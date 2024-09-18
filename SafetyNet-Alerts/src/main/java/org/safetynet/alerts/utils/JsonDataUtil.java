package org.safetynet.alerts.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class JsonDataUtil {

    public static String createPersonMapKey(String firstName, String lastName) {
        return firstName + "-" + lastName;
    }

    public static int getAgeInYears(String dob) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            LocalDate birthDate = LocalDate.parse(dob, dtf);
            LocalDate todayDate = LocalDate.now();

            Period timeBetween = Period.between(birthDate, todayDate);
            return timeBetween.getYears();
        } catch (DateTimeParseException e) {
            return -1;
        }
    }
}
