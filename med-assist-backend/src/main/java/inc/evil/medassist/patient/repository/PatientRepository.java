package inc.evil.medassist.patient.repository;

import inc.evil.medassist.appointment.repository.AppointmentRepository;
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

    @Query(nativeQuery = true, value = """
            SELECT CASE
                       WHEN date_part('year', age(birth_date)) BETWEEN 0 AND 14 THEN '0-14'
                       WHEN date_part('year', age(birth_date)) BETWEEN 15 AND 24 THEN '15-24'
                       WHEN date_part('year', age(birth_date)) BETWEEN 25 AND 64 THEN '25-64'
                       WHEN date_part('year', age(birth_date)) BETWEEN 65 AND 120 THEN '65+'
                       ELSE 'NaN' END as category,
                   count(id)
            FROM patients
            group by category;
            """)
    List<AgeCategoryCountEntry> countAgeCategories();

    @Query(value = "select to_char(created_at, 'Mon-YYYY') as month, count(id) from patients group by to_char(created_at, 'Mon-YYYY')", nativeQuery = true)
    List<PatientsPerMonthCount> countPatientsCreatedPerMonth();

    interface PatientsPerMonthCount {
        String getMonth();

        Long getCount();
    }

    interface AgeCategoryCountEntry {
        String getCategory();
        Long getCount();
    }
}
