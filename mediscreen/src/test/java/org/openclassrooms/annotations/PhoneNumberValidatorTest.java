package org.openclassrooms.annotations;

import com.mongodb.assertions.Assertions;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.openclassrooms.mediscreen.annotations.PhoneNumberValidator;

import static org.mockito.Mockito.mock;

public class PhoneNumberValidatorTest {

    @Test
    void isValid() {
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        String validPhoneNumber = "123-456-7891";
        String invalidPhoneNumber = "21312321132123";
        Assertions.assertTrue(phoneNumberValidator.isValid(validPhoneNumber, context));
        Assertions.assertFalse(phoneNumberValidator.isValid(invalidPhoneNumber, context));
    }
}
