package com.thiagodd.sakura.domain.user;

import com.thiagodd.sakura.domain.validation.Error;
import com.thiagodd.sakura.domain.validation.ValidationHandler;
import com.thiagodd.sakura.domain.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator extends Validator {

    private static final int NAME_MAX_LENGTH = 255;
    private static final int NAME_MIN_LENGTH = 3;
    private static final int LOGIN_MAX_LENGTH = 30;
    private static final int LOGIN_MIN_LENGTH = 3;
    private static final int EMAIL_MAX_LENGTH = 255;
    private static final int EMAIL_MIN_LENGTH = 3;
    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";


    private final User user;

    public UserValidator(final User user, final ValidationHandler handler) {
        super(handler);
        this.user = user;
    }

    @Override
    public void validate() {
        checkFirstNameConstraints();
        checkLastNameConstraints();
        checkEmailConstraints();
        checkLoginConstraints();
    }

    private void checkFirstNameConstraints() {
        validateField("firstName", user.getFirstName(), NAME_MIN_LENGTH, NAME_MAX_LENGTH);
    }

    private void checkLastNameConstraints() {
        validateField("lastName", user.getLogin(), NAME_MIN_LENGTH, NAME_MAX_LENGTH);
    }

    private void checkEmailConstraints() {
        validateField("email", user.getEmail(), EMAIL_MIN_LENGTH, EMAIL_MAX_LENGTH);
        validateEmailField();
    }

    private void checkLoginConstraints() {
        validateField("login", user.getLogin(), LOGIN_MIN_LENGTH, LOGIN_MAX_LENGTH);
        validateLoginField();
    }

    private void validateEmailField() {
        final var email = user.getEmail();
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            this.validationHandler().append(new Error("'email' is in an invalid format"));
        }
    }

    private void validateLoginField(){
        final var username = user.getLogin();

        if (!username.matches("[a-zA-Z0-9._-]+")){
            this.validationHandler().append(new Error("'username' can only contain letters, numbers, dots, underscores, and hyphens"));
        }
    }
}
