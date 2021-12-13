package inc.evil.medassist.appointment.repository;

import inc.evil.medassist.appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    @Query("select a from Appointment a join fetch a.doctor join fetch a.patient")
    List<Appointment> findAppointmentsWithDoctorsAndPatients();

    @Query("select a from Appointment a join fetch a.doctor join fetch a.patient where a.id = :id")
    Optional<Appointment> findByIdWithDoctorsAndPatients(@Param("id") String id);
}
