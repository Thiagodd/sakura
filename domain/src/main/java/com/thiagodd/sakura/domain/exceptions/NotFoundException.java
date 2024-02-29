package com.thiagodd.sakura.domain.exceptions;

import com.thiagodd.sakura.domain.AggregateRoot;
import com.thiagodd.sakura.domain.Identifier;
import com.thiagodd.sakura.domain.validation.Error;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException {

    protected NotFoundException(String message, List<Error> errors){
        super(message, errors);
    }

    public static NotFoundException with(final Class<? extends AggregateRoot<?>> aggregate, final Identifier id){
        final var errorMessage = String.format("%s with ID %s was not found", aggregate.getSimpleName(), id.getValue());

        return new NotFoundException(errorMessage, Collections.emptyList());
    }
}
