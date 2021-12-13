package inc.evil.medassist.appointment.web;

import inc.evil.medassist.appointment.model.Appointment;
import inc.evil.medassist.doctor.web.DoctorResponse;
import inc.evil.medassist.patient.web.PatientResponse;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentResponse {
    private String id;
    private String appointmentDate;
    private String startTime;
    private String endTime;
    private String operation;
    private DoctorResponse doctor;
    private PatientResponse patient;
    private String details;

    public static AppointmentResponse from(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .appointmentDate(appointment.getAppointmentDate().toString())
                .startTime(appointment.getStartTime().toString())
                .endTime(appointment.getEndTime().toString())
                .operation(appointment.getOperation())
                .doctor(DoctorResponse.from(appointment.getDoctor()))
                .patient(PatientResponse.from(appointment.getPatient()))
                .details(appointment.getDetails())
                .build();
    }
}
