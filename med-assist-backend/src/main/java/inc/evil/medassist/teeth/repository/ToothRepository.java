package inc.evil.medassist.teeth.repository;

import inc.evil.medassist.teeth.model.Tooth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Tooth t set t.extracted = true where t.id in :teethIds")
    void markAsExtracted(@Param("teethIds") List<String> teethIds);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Tooth t set t.extracted = false where t.id in :teethIds")
    void markAsNotExtracted(@Param("teethIds") List<String> teethIds);
}
