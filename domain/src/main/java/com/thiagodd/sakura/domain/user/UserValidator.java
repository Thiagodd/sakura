package com.thiagodd.sakura.domain.user;

import com.thiagodd.sakura.domain.validation.Error;
import com.thiagodd.sakura.domain.validation.ValidationHandler;
import com.thiagodd.sakura.domain.validation.Validator;

public class UserValidator extends Validator {

    private static final int NAME_MAX_LENGTH = 255;
    private static final int NAME_MIN_LENGTH = 3;
    private static final int LOGIN_MAX_LENGTH = 30;
    private static final int LOGIN_MIN_LENGTH = 3;
    private static final int EMAIL_MAX_LENGTH = 255;
    private static final int EMAIL_MIN_LENGTH = 3;

    private User user;

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

    private void validateEmailField(){
        final var email = user.getEmail();
        if (email.matches("^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,}$")){
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
