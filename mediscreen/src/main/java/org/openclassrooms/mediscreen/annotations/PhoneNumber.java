package org.openclassrooms.mediscreen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface PhoneNumber {
    String message() default "phone number should be 10 digits with format xxx-xxx-xxxx";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
