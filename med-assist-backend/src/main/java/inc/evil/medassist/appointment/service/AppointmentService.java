package inc.evil.medassist.appointment.service;

import inc.evil.medassist.appointment.model.Appointment;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AppointmentService {
    List<Appointment> findAll();

    Long countAll();

    Map<String, Long> countOperations();

    Map<String, Long> countAppointmentsPerMonth();

    Appointment findById(String id);

    void deleteById(String id);

    Appointment create(Appointment appointmentToCreate);

    List<Appointment> findByDoctorId(String id);

    List<Appointment> findByDoctorIdAndTimeRange(String id, LocalDate startDate, LocalDate endDate);

    Appointment update(String id, Appointment newAppointment);
}
