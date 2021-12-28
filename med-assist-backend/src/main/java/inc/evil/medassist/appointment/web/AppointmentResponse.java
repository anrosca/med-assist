package inc.evil.medassist.appointment.web;

import inc.evil.medassist.appointment.model.Appointment;
import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.doctor.web.DoctorResponse;
import inc.evil.medassist.patient.web.PatientResponse;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Collectors;

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

    public static AppointmentResponse from(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .startDate(appointment.getAppointmentDate().atTime(appointment.getStartTime()).toString())
                .endDate(appointment.getAppointmentDate().atTime(appointment.getEndTime()).toString())
                .operation(appointment.getOperation())
                .doctor(makeDoctor(appointment.getDoctor()))
                .patient(PatientResponse.from(appointment.getPatient()))
                .details(appointment.getDetails())
                .build();
    }

    private static DoctorResponse makeDoctor(Doctor doctor) {
        return DoctorResponse.builder()
                .id(doctor.getId())
                .email(doctor.getEmail())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .username(doctor.getUsername())
                .specialty(doctor.getSpecialty().name())
                .telephoneNumber(doctor.getTelephoneNumber())
                .enabled(doctor.isEnabled())
                .build();
    }
}
