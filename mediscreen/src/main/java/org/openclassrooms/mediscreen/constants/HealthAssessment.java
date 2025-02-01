package org.openclassrooms.mediscreen.constants;

public enum HealthAssessment {
    NONE("None")
    /*    None - patient has no doctor’s notes containing any of the trigger terminology. */,

    BORDERLINE("Borderline")
    /*    Borderline - patient has a reference to two triggers, and is over 30 years of age. */,

    IN_DANGER("In danger")
    /*    In danger - depends on patient’s age and sex.
        If under 30 and female, four trigger terms.
        If either over 30, then six.
        If under 30, and male, then three trigger terms. */,

    EARLY_ONSET("Early Onset");
/*    Early Onset - again, depends on age and sex.
        If under 30, and male, then five trigger terms.
        If under 30 and female, seven trigger terms.
        If over 30, then eight or more. */

    private final String value;

    HealthAssessment(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
