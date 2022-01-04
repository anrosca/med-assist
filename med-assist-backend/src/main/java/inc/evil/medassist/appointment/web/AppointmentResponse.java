package inc.evil.medassist.appointment.web;

import inc.evil.medassist.appointment.model.Appointment;
import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.doctor.web.DoctorResponse;
import inc.evil.medassist.patient.web.PatientResponse;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentResponse {
    private String id;
    private String startDate;
    private String endDate;
    private String operation;
    private DoctorResponse doctor;
    private PatientResponse patient;
    private String details;
    private Color color;

    public static AppointmentResponse from(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .startDate(appointment.getAppointmentDate().atTime(appointment.getStartTime()).toString())
                .endDate(appointment.getAppointmentDate().atTime(appointment.getEndTime()).toString())
                .operation(appointment.getOperation())
                .doctor(DoctorResponse.simpleForm(appointment.getDoctor()))
                .patient(PatientResponse.from(appointment.getPatient()))
                .details(appointment.getDetails())
                .color(Color.from(appointment.getColor()))
                .build();
    }
}
