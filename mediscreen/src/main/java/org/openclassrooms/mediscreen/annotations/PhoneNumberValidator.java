package org.openclassrooms.mediscreen.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    private static final String REGEX = "\\d{3}-\\d{3}-\\d{4}";

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        return phone.matches(REGEX);
    }

}
