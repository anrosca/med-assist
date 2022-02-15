package inc.evil.medassist.treatmentplan.repository;

import inc.evil.medassist.treatmentplan.model.TreatmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlan, String> {
    @Query("select tp from TreatmentPlan tp join fetch tp.patient")
    List<TreatmentPlan> findAll();

    @Query("select tp from TreatmentPlan tp join fetch tp.patient where tp.id = :id")
    Optional<TreatmentPlan> findById(@NonNull @Param("id") String id);
}
