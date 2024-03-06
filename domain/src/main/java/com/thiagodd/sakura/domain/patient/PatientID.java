package com.thiagodd.sakura.domain.patient;

import com.thiagodd.sakura.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class PatientID extends Identifier {

    private final String value;

    private PatientID(final String value) {
        Objects.requireNonNull(value, "'id' should not be null");

        this.value = value;
    }

    public static PatientID unique(){
        return PatientID.from(UUID.randomUUID());
    }

    public static PatientID from(final String id){
        return new PatientID(id);
    }

    public static PatientID from(final UUID id){
        return PatientID.from(id.toString());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (PatientID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
