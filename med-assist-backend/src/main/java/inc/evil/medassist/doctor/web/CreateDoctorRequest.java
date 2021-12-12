package inc.evil.medassist.doctor.web;

import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.doctor.model.Speciality;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateDoctorRequest {
    @NotBlank(message = "{create.doctor.name.not.blank}")
    private String name;

    @NotBlank(message = "{create.doctor.speciality.not.blank}")
    private String speciality;

    public Doctor toDoctor() {
        return Doctor.builder()
                .name(name)
                .speciality(Speciality.valueOf(speciality))
                .build();
    }
}
