package com.thiagodd.sakura.domain.patient;

import com.thiagodd.sakura.domain.pagination.Pagination;
import com.thiagodd.sakura.domain.pagination.SearchQuery;

import java.util.Optional;

public interface PatientGateway {

    Patient create(Patient patient);

    void deleteById(PatientID id);

    Optional<Patient> findById(PatientID patientID);

    Patient update(Patient patient);

    Pagination<Patient> findAll(SearchQuery query);
}
