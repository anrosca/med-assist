package inc.evil.medassist.appointment.facade;

import inc.evil.medassist.appointment.web.AppointmentResponse;
import inc.evil.medassist.appointment.web.CreateAppointmentRequest;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentFacade {
    List<AppointmentResponse> findAll();

    AppointmentResponse findById(String id);

    void deleteById(String id);

    AppointmentResponse create(CreateAppointmentRequest request);

    List<AppointmentResponse> findAppointmentsByDoctorId(String doctorId);

    List<AppointmentResponse> findAppointmentsByDoctorIdInTimeRange(String doctorId, LocalDate startDate, LocalDate endDate);
}
