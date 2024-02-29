package com.thiagodd.sakura.domain.patient;

import com.thiagodd.sakura.domain.AggregateRoot;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

public class Patient extends AggregateRoot<PatientID> implements Cloneable {

    private String name;
    private String socialName;
    private LocalDate dateOfBirth;
    private String email;
    private String observation;
    private boolean active;
    private Sex sex;

    private final Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Patient(
        final PatientID id,
        final String name,
        final String socialName,
        final LocalDate dateOfBirth,
        final String email,
        final String observation,
        final boolean isActive,
        final Sex sex,
        final Instant createdAt,
        final Instant updatedAt,
        final Instant deletedAt
    ) {
        super(id);
        this.name = name;
        this.socialName = socialName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.observation = observation;
        this.active = isActive;
        this.sex = sex;
        this.createdAt = Objects.requireNonNull(createdAt, "'created_at' should not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "'updated_at' should not be null");
        this.deletedAt = deletedAt;
    }

    public static Patient newPatient(
        final String name,
        final String socialName,
        final LocalDate dateOfBirth,
        final String email,
        final String observation,
        final boolean isActive,
        final Sex sex
    ) {
        final var id = PatientID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;

        return new Patient(id,
            name,
            socialName,
            dateOfBirth,
            email,
            observation,
            isActive,
            sex,
            now,
            now,
            deletedAt);
    }

    public static Patient with(
        final PatientID id,
        final String name,
        final String socialName,
        final LocalDate dateOfBirth,
        final String email,
        final String observation,
        final boolean isActive,
        final Sex sex,
        final Instant createdAt,
        final Instant updatedAt,
        final Instant deletedAt
    ) {
        return new Patient(
            id,
            name,
            socialName,
            dateOfBirth,
            email,
            observation,
            isActive,
            sex,
            createdAt,
            updatedAt,
            deletedAt
        );
    }

    public static Patient with(final Patient patient) {
        return with(
            patient.id,
            patient.name,
            patient.socialName,
            patient.dateOfBirth,
            patient.email,
            patient.observation,
            patient.active,
            patient.sex,
            patient.createdAt,
            patient.updatedAt,
            patient.deletedAt
        );
    }

    public Patient activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();

        return this;
    }

    public Patient deactivate() {
        if (getDeletedAt() == null) this.deletedAt = Instant.now();
        this.active = false;
        this.updatedAt = Instant.now();

        return this;
    }

    public Patient update(
        final String name,
        final String socialName,
        final LocalDate dateOfBirth,
        final String email,
        final String observation,
        final boolean isActive,
        final Sex sex
    ) {
        if (isActive) {
            activate();
        } else {
            deactivate();
        }

        this.name = name;
        this.socialName = socialName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.observation = observation;
        this.sex = sex;

        this.updatedAt = Instant.now();
        return this;

    }

    public Patient update(Patient patient) {
        return update(
            patient.name,
            patient.socialName,
            patient.dateOfBirth,
            patient.email,
            patient.observation,
            patient.isActive(),
            patient.sex
        );
    }

    public String getName() {
        return name;
    }

    public String getSocialName() {
        return socialName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getObservation() {
        return observation;
    }

    public boolean isActive() {
        return active;
    }

    public Sex getSex() {
        return sex;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    @Override
    public Patient clone(){
        try{
            return (Patient) super.clone();
        }catch (CloneNotSupportedException exception){
            throw new AssertionError();
        }
    }
}
