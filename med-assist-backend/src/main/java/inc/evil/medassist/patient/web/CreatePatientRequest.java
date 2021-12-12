package inc.evil.medassist.patient.web;

import inc.evil.medassist.patient.model.Patient;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CreatePatientRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private LocalDate birthDate;

    public Patient toPatient() {
        return Patient.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .birthDate(birthDate)
                .build();
    }
}
