package inc.evil.medassist.doctor.web;

import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.doctor.model.Speciality;
import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class DoctorResponse {
    private String id;
    private String name;
    private Speciality speciality;

    public static DoctorResponse from(Doctor doctor) {
        return DoctorResponse.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .speciality(doctor.getSpeciality())
                .build();
    }
}
