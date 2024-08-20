package com.openclassrooms.watchlist.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BadMovieValidator.class)
public @interface BadMovie {

    String message() default "If a movie is bad as 6 then comment should be at least 15 characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
