package inc.evil.medassist.teeth.repository;

import inc.evil.medassist.teeth.model.Tooth;
import inc.evil.medassist.treatment.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ToothRepository extends JpaRepository<Tooth, String> {
    Set<Tooth> findAllByIdIn(List<String> ids);

    @Query("select a from Tooth a join fetch a.patient patient where patient.id = :patientId")
    Set<Tooth> findAllByPatientIdWithPatient(@Param("patientId") String id);
}
