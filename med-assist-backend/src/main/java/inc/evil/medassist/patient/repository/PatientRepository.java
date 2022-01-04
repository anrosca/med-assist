package inc.evil.medassist.patient.repository;

import inc.evil.medassist.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    @Query("select p from Patient p where p.isDeleted = false")
    List<Patient> findAllNonDeleted();

    @Query("select p from Patient p where p.isDeleted = false and p.id = :id")
    Optional<Patient> findByIdNonDeleted(String id);
}
