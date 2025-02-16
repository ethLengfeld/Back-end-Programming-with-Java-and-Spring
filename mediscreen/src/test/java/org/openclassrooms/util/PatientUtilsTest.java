package org.openclassrooms.util;

import org.junit.jupiter.api.Test;
import org.openclassrooms.mediscreen.util.PatientUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientUtilsTest {

    @Test
    void calculateAge() {
        String dob = "1998-04-13";
        int age = PatientUtils.calculateAge(dob);
        assertEquals(26, age);

        String badDobFormat = "12-12-1900";
        age = PatientUtils.calculateAge(badDobFormat);
        assertEquals(-1, age);
    }
}
