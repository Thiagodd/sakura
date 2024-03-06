package com.thiagodd.sakura.domain.validation;

public abstract class Validator {

    private final ValidationHandler handler;

    public Validator(ValidationHandler handler) {
        this.handler = handler;
    }

    protected void validateField(String fieldName, String value, int minLength, int maxLength){
        if (value == null) {
            this.validationHandler().append(new Error("'" + fieldName + "' should not be null"));
        } else if (value.isBlank()){
            this.validationHandler().append(new Error("'" + fieldName + "' should not be empty"));
        } else if (value.trim().length() < minLength || value.trim().length() > maxLength) {
            this.validationHandler().append(new Error("'" + fieldName + "' length should be between " + minLength + " and " + maxLength + " characters"));
        }
    }

    public abstract void validate();

    protected ValidationHandler validationHandler(){
        return this.handler;
    }

}
