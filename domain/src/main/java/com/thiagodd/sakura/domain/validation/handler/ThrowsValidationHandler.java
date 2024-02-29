package com.thiagodd.sakura.domain.validation.handler;

import com.thiagodd.sakura.domain.exceptions.DomainException;
import com.thiagodd.sakura.domain.validation.Error;
import com.thiagodd.sakura.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {

    @Override
    public ValidationHandler append(Error error) {
        throw DomainException.with(error);
    }

    @Override
    public ValidationHandler append(ValidationHandler handler) {
        throw DomainException.with(handler.getErrors());
    }

    @Override
    public ValidationHandler validate(Validation validation) {
        try {
            validation.validate();
        } catch (final Exception exception) {
            throw DomainException.with(new Error(exception.getMessage()));
        }

        return this;
    }

    @Override
    public List<Error> getErrors() {
        return null;
    }
}
