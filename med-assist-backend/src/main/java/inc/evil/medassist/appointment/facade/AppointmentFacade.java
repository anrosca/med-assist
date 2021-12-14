package inc.evil.medassist.appointment.facade;

import inc.evil.medassist.appointment.web.AppointmentResponse;
import inc.evil.medassist.appointment.web.CreateAppointmentRequest;

import java.util.List;

public interface AppointmentFacade {
    List<AppointmentResponse> findAll();

    AppointmentResponse findById(String id);

    void deleteById(String id);

    AppointmentResponse create(CreateAppointmentRequest request);
}
