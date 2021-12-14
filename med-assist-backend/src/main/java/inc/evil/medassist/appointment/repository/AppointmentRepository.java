package inc.evil.medassist.appointment.repository;

import inc.evil.medassist.appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    @Query("select a from Appointment a join fetch a.doctor join fetch a.patient")
    List<Appointment> findAppointmentsWithDoctorsAndPatients();

    @Query("select a from Appointment a join fetch a.doctor join fetch a.patient where a.id = :id")
    Optional<Appointment> findByIdWithDoctorsAndPatients(@Param("id") String id);

    @Query("""
            select a from Appointment a where a.doctor.id = :doctorId and a.appointmentDate = :date
                    and (:startTime < a.endTime and :endTime > a.startTime)
    """)
    Optional<Appointment> findConflictingAppointment(@Param("doctorId") String doctorId, @Param("date") LocalDate date, LocalTime startTime, LocalTime endTime);
}
