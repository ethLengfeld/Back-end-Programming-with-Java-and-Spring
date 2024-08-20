package com.openclassrooms.watchlist.annotations;

import com.openclassrooms.watchlist.model.WatchlistItem;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BadMovieValidator implements ConstraintValidator<BadMovie, WatchlistItem>{

    @Override
    public boolean isValid(WatchlistItem value, ConstraintValidatorContext context) {

        return !(Double.valueOf(value.getRating()) < 6 && value.getComment().length() < 15);
    }
}
