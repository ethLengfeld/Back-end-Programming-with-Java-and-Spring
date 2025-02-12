package org.openclassrooms.mediscreen.repository;

import org.openclassrooms.mediscreen.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query
    Optional<Patient> findByFamily(@Param("family") String family);
}
