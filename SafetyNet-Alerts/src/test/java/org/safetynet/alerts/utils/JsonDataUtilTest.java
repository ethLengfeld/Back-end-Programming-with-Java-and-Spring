package org.safetynet.alerts.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonDataUtilTest {

    @Test
    public void testCreatePersonMapKey() {
        String firstName = "Ethan";
        String lastName = "Lengfeld";
        String key = JsonDataUtil.createPersonMapKey(firstName, lastName);
        assertEquals("Ethan-Lengfeld", key);
    }

    @Test
    public void testGetAgeInYears() {
        String birthdate = "04/13/1998";
        int age = JsonDataUtil.getAgeInYears(birthdate);
        assertEquals(26, age);

        String badBirthdate = "April 13, 1998";
        age = JsonDataUtil.getAgeInYears(badBirthdate);
        assertEquals(-1, age);
    }
}
