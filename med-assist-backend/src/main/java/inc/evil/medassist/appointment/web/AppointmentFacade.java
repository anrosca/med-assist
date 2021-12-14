package inc.evil.medassist.appointment.web;

import java.util.List;

public interface AppointmentFacade {
    List<AppointmentResponse> findAll();

    AppointmentResponse findById(String id);

    void deleteById(String id);

    AppointmentResponse create(CreateAppointmentRequest request);
}
