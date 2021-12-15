package inc.evil.medassist.appointment.service;

import inc.evil.medassist.appointment.model.Appointment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    List<Appointment> findAll();

    Appointment findById(String id);

    void deleteById(String id);

    Appointment create(Appointment appointmentToCreate);

    List<Appointment> findByDoctorId(String id);

    List<Appointment> findByDoctorIdAndTimeRange(String id, LocalDate startDate, LocalDate endDate);
}
