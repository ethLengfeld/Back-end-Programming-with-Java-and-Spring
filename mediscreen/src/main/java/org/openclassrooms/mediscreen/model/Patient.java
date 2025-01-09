package org.openclassrooms.mediscreen.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Patient {

    private String familyName;
    private String givenName;
    private String dateOfBirth;
    private char sex;
    private String address;
    private String phoneNumber;

}
