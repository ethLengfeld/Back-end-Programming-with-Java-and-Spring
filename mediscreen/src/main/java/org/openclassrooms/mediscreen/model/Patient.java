package org.openclassrooms.mediscreen.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Patient {

    private String family;
    private String given;
    private String dob;
    private char sex;
    private String address;
    private String phone;

}
