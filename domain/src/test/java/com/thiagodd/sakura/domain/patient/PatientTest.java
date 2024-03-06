package com.thiagodd.sakura.domain.patient;

import com.thiagodd.sakura.domain.exceptions.DomainException;
import com.thiagodd.sakura.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    @Test
    public void givenAValidParams_whenCallNewPatient_thenInstantiateAPatient() {
        final var expectedName = "João da Silva";
        final var expectedSocialName = "João";
        final var expectedDateOfBirth = LocalDate.of(1990, 5, 15);
        final var expectedEmail = "Joao.silva@example.com";
        final var expectedObservation = "Paciente com histórico de alergia a penicilina.";
        final var expectedIsActive = true;
        final var expectedSex = Sex.MALE;

        final var actualPatient = Patient.newPatient(
            expectedName, expectedSocialName, expectedDateOfBirth, expectedEmail, expectedObservation, expectedIsActive, expectedSex);


        assertNotNull(actualPatient);
        assertNotNull(actualPatient.getId());
        assertEquals(expectedName, actualPatient.getName());
        assertEquals(expectedSocialName, actualPatient.getSocialName());
        assertEquals(expectedDateOfBirth, actualPatient.getDateOfBirth());
        assertEquals(expectedEmail, actualPatient.getEmail());
        assertEquals(expectedObservation, actualPatient.getObservation());
        assertEquals(expectedIsActive, actualPatient.isActive());
        assertEquals(expectedSex, actualPatient.getSex());
        assertNotNull(actualPatient.getCreatedAt());
        assertNotNull(actualPatient.getUpdatedAt());
        assertNull(actualPatient.getDeletedAt());
    }

    @Test
    public void givenAValidEmptyObservation_whenCallNewPatient_thenInstantiateAPatient() {
        final var expectedName = "João da Silva";
        final var expectedSocialName = "João";
        final var expectedDateOfBirth = LocalDate.of(1990, 5, 15);
        final var expectedEmail = "Joao.silva@example.com";
        final var expectedObservation = "   ";
        final var expectedIsActive = true;
        final var expectedSex = Sex.MALE;

        final var actualPatient = Patient.newPatient(
            expectedName, expectedSocialName, expectedDateOfBirth, expectedEmail, expectedObservation, expectedIsActive, expectedSex);

        assertDoesNotThrow(() -> actualPatient.validate(new ThrowsValidationHandler()));

        assertNotNull(actualPatient);
        assertNotNull(actualPatient.getId());
        assertEquals(expectedName, actualPatient.getName());
        assertEquals(expectedSocialName, actualPatient.getSocialName());
        assertEquals(expectedDateOfBirth, actualPatient.getDateOfBirth());
        assertEquals(expectedEmail, actualPatient.getEmail());
        assertEquals(expectedObservation, actualPatient.getObservation());
        assertEquals(expectedIsActive, actualPatient.isActive());
        assertEquals(expectedSex, actualPatient.getSex());
        assertNotNull(actualPatient.getCreatedAt());
        assertNotNull(actualPatient.getUpdatedAt());
        assertNull(actualPatient.getDeletedAt());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewPatientAndValidate_thenShouldReceiveError() {
        final var expectedSocialName = "João";
        final var expectedDateOfBirth = LocalDate.of(1990, 5, 15);
        final var expectedEmail = "Joao.silva@example.com";
        final var expectedObservation = "   ";
        final var expectedIsActive = true;
        final var expectedSex = Sex.MALE;

        final var exceptedErrorMessage = "'name' should not be null";
        final var exceptedErrorCount = 1;

        final var actualPatient = Patient.newPatient(
            null, expectedSocialName, expectedDateOfBirth, expectedEmail, expectedObservation, expectedIsActive, expectedSex);

        final var actualException = assertThrows(DomainException.class, () -> actualPatient.validate(new ThrowsValidationHandler()));

        assertEquals(exceptedErrorCount, actualException.getErrors().size());
        assertEquals(exceptedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewPatientAndValidate_thenShouldReceiveError() {
        final String expectedName = "  ";
        final var expectedSocialName = "João";
        final var expectedDateOfBirth = LocalDate.of(1990, 5, 15);
        final var expectedEmail = "Joao.silva@example.com";
        final var expectedObservation = "   ";
        final var expectedIsActive = true;
        final var expectedSex = Sex.MALE;

        final var exceptedErrorMessage = "'name' should not be empty";
        final var exceptedErrorCount = 1;

        final var actualPatient = Patient.newPatient(
            expectedName, expectedSocialName, expectedDateOfBirth, expectedEmail, expectedObservation, expectedIsActive, expectedSex);

        final var actualException = assertThrows(DomainException.class, () -> actualPatient.validate(new ThrowsValidationHandler()));

        assertEquals(exceptedErrorCount, actualException.getErrors().size());
        assertEquals(exceptedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewPatientAndValidate_thenShouldReceiveError() {
        final String expectedName = "AB ";
        final var expectedSocialName = "João";
        final var expectedDateOfBirth = LocalDate.of(1990, 5, 15);
        final var expectedEmail = "Joao.silva@example.com";
        final var expectedObservation = "   ";
        final var expectedIsActive = true;
        final var expectedSex = Sex.MALE;

        final var exceptedErrorMessage = "'name' length should be between 3 and 255 characters";
        final var exceptedErrorCount = 1;

        final var actualPatient = Patient.newPatient(
            expectedName, expectedSocialName, expectedDateOfBirth, expectedEmail, expectedObservation, expectedIsActive, expectedSex);

        final var actualException = assertThrows(DomainException.class, () -> actualPatient.validate(new ThrowsValidationHandler()));

        assertEquals(exceptedErrorCount, actualException.getErrors().size());
        assertEquals(exceptedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameLengthMoreThan255_whenCallNewPatientAndValidate_thenShouldReceiveError() {
        final var expectedName = """
             O incentivo ao avanço tecnológico, assim como o novo modelo estrutural aqui preconizado prepara-nos para enfrentar
             situações atípicas decorrentes dos paradigmas corporativos. Ainda assim, existem dúvidas a respeito de como a
             constante divulgação das informações assume importantes posições no estabelecimento do investimento em reciclagem
             técnica.
            """;
        final var expectedSocialName = "João";
        final var expectedDateOfBirth = LocalDate.of(1990, 5, 15);
        final var expectedEmail = "Joao.silva@example.com";
        final var expectedObservation = "   ";
        final var expectedIsActive = true;
        final var expectedSex = Sex.MALE;

        final var exceptedErrorMessage = "'name' length should be between 3 and 255 characters";
        final var exceptedErrorCount = 1;

        final var actualPatient = Patient.newPatient(
            expectedName, expectedSocialName, expectedDateOfBirth, expectedEmail, expectedObservation, expectedIsActive, expectedSex);

        final var actualException = assertThrows(DomainException.class, () -> actualPatient.validate(new ThrowsValidationHandler()));

        assertEquals(exceptedErrorCount, actualException.getErrors().size());
        assertEquals(exceptedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidActivePatient_whenCallDeactivate_thenReturnInactivePatient() {
        final var expectedName = "João da Silva";
        final var expectedSocialName = "João";
        final var expectedDateOfBirth = LocalDate.of(1990, 5, 15);
        final var expectedEmail = "Joao.silva@example.com";
        final var expectedObservation = "Paciente com histórico de alergia a penicilina.";
        final var expectedIsActive = false;
        final var expectedSex = Sex.MALE;

        final var patient = Patient.newPatient(
            expectedName, expectedSocialName, expectedDateOfBirth, expectedEmail, expectedObservation, true, expectedSex);

        assertDoesNotThrow(() -> patient.validate(new ThrowsValidationHandler()));

        final var createdAt = patient.getCreatedAt();
        final var updatedAt = patient.getUpdatedAt();

        final var actualPatient = patient.deactivate();

        Assertions.assertDoesNotThrow(() -> patient.validate(new ThrowsValidationHandler()));
        Assertions.assertDoesNotThrow(() -> actualPatient.validate(new ThrowsValidationHandler()));
        assertEquals(patient.getId(), actualPatient.getId());
        assertEquals(expectedName, actualPatient.getName());
        assertEquals(expectedSocialName, actualPatient.getSocialName());
        assertEquals(expectedDateOfBirth, actualPatient.getDateOfBirth());
        assertEquals(expectedEmail, actualPatient.getEmail());
        assertEquals(expectedObservation, actualPatient.getObservation());
        assertEquals(expectedIsActive, actualPatient.isActive());
        assertEquals(expectedSex, actualPatient.getSex());
        assertEquals(createdAt, actualPatient.getCreatedAt());
        assertTrue(actualPatient.getUpdatedAt().isAfter(updatedAt));
        assertNotNull(actualPatient.getDeletedAt());
    }

    @Test
    public void givenAValidInactivePatient_whenCallActivate_thenReturnActivePatient() {
        final var expectedName = "João da Silva";
        final var expectedSocialName = "João";
        final var expectedDateOfBirth = LocalDate.of(1990, 5, 15);
        final var expectedEmail = "Joaosilva@example.com";
        final var expectedObservation = "Paciente com histórico de alergia a penicilina.";
        final var expectedIsActive = true;
        final var expectedSex = Sex.MALE;

        final var patient = Patient.newPatient(
            expectedName, expectedSocialName, expectedDateOfBirth, expectedEmail, expectedObservation, false, expectedSex);

        assertDoesNotThrow(() -> patient.validate(new ThrowsValidationHandler()));

        final var createdAt = patient.getCreatedAt();
        final var updatedAt = patient.getUpdatedAt();

        assertFalse(patient.isActive());
        assertNotNull(patient.getDeletedAt());

        final var actualPatient = patient.activate();

        Assertions.assertDoesNotThrow(() -> patient.validate(new ThrowsValidationHandler()));
        Assertions.assertDoesNotThrow(() -> actualPatient.validate(new ThrowsValidationHandler()));

        assertEquals(patient.getId(), actualPatient.getId());
        assertEquals(expectedName, actualPatient.getName());
        assertEquals(expectedSocialName, actualPatient.getSocialName());
        assertEquals(expectedDateOfBirth, actualPatient.getDateOfBirth());
        assertEquals(expectedEmail, actualPatient.getEmail());
        assertEquals(expectedObservation, actualPatient.getObservation());
        assertEquals(expectedIsActive, actualPatient.isActive());
        assertEquals(expectedSex, actualPatient.getSex());
        assertEquals(createdAt, actualPatient.getCreatedAt());
        assertTrue(actualPatient.getUpdatedAt().isAfter(updatedAt));
        assertNull(actualPatient.getDeletedAt());
    }



    @Test
    public void givenAValidPatient_whenCallUpdateToInactive_thenReturnPatientUpdated() {
        final var expectedName = "João da Silva";
        final var expectedSocialName = "João";
        final var expectedDateOfBirth = LocalDate.of(1990, 5, 15);
        final var expectedEmail = "Joao.silva@example.com";
        final var expectedObservation = "Paciente com histórico de alergia a penicilina.";
        final var expectedSex = Sex.MALE;
        final var expectedIsActive = false;

        final var patient = Patient.newPatient(
            "Joao De Silva",
            "Não têm",
            LocalDate.of(1990, 5, 14),
            "joaosilva@example.com.br",
            "Paciente com histórico de alergia a dipirona.",
            true,
            Sex.MALE
        );

        assertDoesNotThrow(() -> patient.validate(new ThrowsValidationHandler()));
        assertTrue(patient.isActive());
        assertNull(patient.getDeletedAt());

        final var createdAt = patient.getCreatedAt();
        final var updatedAt = patient.getUpdatedAt();

        final var actualPatient =
            patient.update(expectedName, expectedSocialName, expectedDateOfBirth, expectedEmail, expectedObservation, expectedIsActive, expectedSex);


        assertDoesNotThrow(() -> actualPatient.validate(new ThrowsValidationHandler()));

        assertEquals(patient.getId(), actualPatient.getId());
        assertEquals(expectedName, actualPatient.getName());
        assertEquals(expectedSocialName, actualPatient.getSocialName());
        assertEquals(expectedDateOfBirth, actualPatient.getDateOfBirth());
        assertEquals(expectedEmail, actualPatient.getEmail());
        assertEquals(expectedObservation, actualPatient.getObservation());
        assertEquals(expectedIsActive, actualPatient.isActive());
        assertEquals(expectedSex, actualPatient.getSex());
        assertEquals(createdAt, actualPatient.getCreatedAt());
        assertTrue(actualPatient.getUpdatedAt().isAfter(updatedAt));
        assertNotNull(actualPatient.getDeletedAt());
    }
}