package com.openclassrooms.watchlist.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.RetentionPolicy;

@Target ({ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
@Constraint (validatedBy = PriorityValidator.class)
public @interface Priority {

    String message() default "Please enter L (for Low), M (for Medium), or H (for High) for priority";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
