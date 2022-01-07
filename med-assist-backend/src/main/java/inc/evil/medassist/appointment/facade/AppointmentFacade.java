package inc.evil.medassist.appointment.facade;

import inc.evil.medassist.appointment.web.AppointmentResponse;
import inc.evil.medassist.appointment.web.UpsertAppointmentRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AppointmentFacade {
    List<AppointmentResponse> findAll();

    AppointmentResponse findById(String id);

    Long countAll();

    Map<String, Long> countOperations();

    Map<String, Long> countAppointmentsPerMonth();

    void deleteById(String id);

    AppointmentResponse create(UpsertAppointmentRequest request);

    List<AppointmentResponse> findAppointmentsByDoctorId(String doctorId);

    List<AppointmentResponse> findAppointmentsByDoctorIdInTimeRange(String doctorId, LocalDate startDate, LocalDate endDate);

    AppointmentResponse update(String id, UpsertAppointmentRequest request);
}
