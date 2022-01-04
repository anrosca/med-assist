package inc.evil.medassist.patient.web;

import inc.evil.medassist.patient.model.Patient;
import inc.evil.medassist.teeth.web.ToothResponse;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PatientResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String birthDate;

    public static PatientResponse from(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .birthDate(patient.getBirthDate().toString())
                .phoneNumber(patient.getPhoneNumber())
                .build();
    }
}
