package com.thiagodd.sakura.domain.patient;

import com.thiagodd.sakura.domain.validation.Error;
import com.thiagodd.sakura.domain.validation.ValidationHandler;
import com.thiagodd.sakura.domain.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatientValidator extends Validator {

    private static final int NAME_MAX_LENGTH = 255;
    private static final int NAME_MIN_LENGTH = 3;
    private static final int EMAIL_MAX_LENGTH = 255;
    private static final int EMAIL_MIN_LENGTH = 3;
    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";


    private final Patient patient;

    public PatientValidator(final Patient patient, final ValidationHandler handler) {
        super(handler);
        this.patient = patient;
    }

    @Override
    public void validate() {
        checkNameConstraints();
        checkEmailConstraints();
    }

    private void checkNameConstraints() {
        validateField("name", patient.getName(), NAME_MIN_LENGTH, NAME_MAX_LENGTH);
    }

    private void checkEmailConstraints() {
        validateField("email", patient.getEmail(), EMAIL_MIN_LENGTH, EMAIL_MAX_LENGTH);
        validateEmailField();
    }

    private void validateEmailField() {
        final var email = patient.getEmail();
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            this.validationHandler().append(new Error("'email' is in an invalid format"));
        }
    }
}
