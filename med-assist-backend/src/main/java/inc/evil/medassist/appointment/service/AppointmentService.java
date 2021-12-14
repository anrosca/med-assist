package inc.evil.medassist.appointment.service;

import inc.evil.medassist.appointment.model.Appointment;

import java.util.List;

public interface AppointmentService {
    List<Appointment> findAll();

    Appointment findById(String id);

    void deleteById(String id);

    Appointment create(Appointment appointmentToCreate);
}
