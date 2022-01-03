package inc.evil.medassist.treatment.repository;

import inc.evil.medassist.treatment.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, String> {
    @Query("select distinct a from Treatment a join fetch a.doctor join fetch a.patient join fetch a.teeth")
    List<Treatment> findTreatmentsWithDoctorsAndPatientsAndTeeth();

    @Query("select distinct a from Treatment a join fetch a.doctor join fetch a.patient join fetch a.teeth where a.id = :id")
    Optional<Treatment> findByIdWithDoctorsAndPatientsAndTeeth(@Param("id") String id);

    @Query("select distinct a from Treatment a join fetch a.doctor join fetch a.patient join fetch a.teeth where a.patient.id = :patientId")
    List<Treatment> findByPatientIdWithDoctorsAndPatientsAndTeeth(@Param("patientId") String id);

    @Query("select distinct a from Treatment a join fetch a.doctor join fetch a.patient join fetch a.teeth where a.doctor.id = :doctorId")
    List<Treatment> findByDoctorIdWithDoctorsAndPatientsAndTeeth(@Param("doctorId") String id);

    @Query("select distinct a from Treatment a join fetch a.doctor join fetch a.patient join fetch a.teeth teeth where teeth.id = :toothId")
    List<Treatment> findByToothIdWithDoctorsAndPatientsAndTeeth(@Param("toothId") String id);
    
}
