package org.openclassrooms.mediscreen.annotations;

import org.openclassrooms.mediscreen.model.Patient;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, Patient>{

    @Override
    public boolean isValid(Patient patient, ConstraintValidatorContext context) {

        return patient.getPhone().length() > 0;
    }

}
