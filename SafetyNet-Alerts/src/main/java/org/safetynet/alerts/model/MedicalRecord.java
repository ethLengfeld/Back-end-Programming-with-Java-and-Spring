package org.safetynet.alerts.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalRecord {
    /**
     *     {
     *       "firstName": "John",
     *       "lastName": "Boyd",
     *       "birthdate": "03/06/1984",
     *       "medications": [
     *         "aznol:350mg",
     *         "hydrapermazol:100mg"
     *       ],
     *       "allergies": [
     *         "nillacilan"
     *       ]
     *     }
     */
    private String firstName;
    private String lastName;
    private String birthdate;
    private String[] medications;
    private String[] allergies;
}