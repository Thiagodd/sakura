package com.thiagodd.sakura.domain.patient;

import com.thiagodd.sakura.domain.validation.Error;
import com.thiagodd.sakura.domain.validation.ValidationHandler;
import com.thiagodd.sakura.domain.validation.Validator;

import java.util.regex.Pattern;

public class PatientValidator extends Validator {

    private static final int ATTRIBUTE_MAX_LENGTH = 255;
    private static final int ATTRIBUTE_MIN_LENGTH = 3;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private Patient patient;

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
        final var name = this.patient.getName();
        IsNullValidate(name, "'name' should not be null");
        IsBlankValidate(name, "'name' should not be empty");
        isValidLengthValidate(name, "'name' must be between 3 and 255 characters");
    }

    private void checkEmailConstraints() {
        final var email = this.patient.getEmail();
        IsNullValidate(email, "'email' should not be null");
        IsBlankValidate(email, "'email' should not be empty");
        isValidEmailTemplate(email, "'email' is an invalid format");
        isValidLengthValidate(email, "'email' must be between 3 and 255 characters");
    }

    private void isValidLengthValidate(String email, String message) {
        final int length = email.trim().length();
        if (length > ATTRIBUTE_MAX_LENGTH || length < ATTRIBUTE_MIN_LENGTH) {
            this.validationHandler().append(new Error(message));
        }
    }

    private void isValidEmailTemplate(final String email, final String message) {
        final var pattern = Pattern.compile(EMAIL_PATTERN);
        final var matcher = pattern.matcher(email);

        if (matcher.matches()){
            this.validationHandler().append(new Error(message));
        }
    }

    private void IsNullValidate(final String attribute, final String message) {
        if (attribute == null) {
            this.validationHandler().append(new Error(message));
        }
    }

    private void IsBlankValidate(final String attribute, final String message){
        if(attribute.isBlank()){
            this.validationHandler().append(new Error(message));
        }
    }
}
