package inc.evil.medassist.doctor.web;

import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.user.model.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DoctorResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Set<String> authorities;
    private String specialty;
    private String telephoneNumber;
    private boolean enabled;

    private String accessToken;

    public static DoctorResponse from(Doctor doctor) {
        return DoctorResponse.builder()
                .id(doctor.getId())
                .email(doctor.getEmail())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .username(doctor.getUsername())
                .authorities(doctor.getAuthorities().stream().map(
                        GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .specialty(doctor.getSpecialty().name())
                .telephoneNumber(doctor.getTelephoneNumber())
                .enabled(doctor.isEnabled())
                .build();
    }

    public static DoctorResponse simpleForm(Doctor doctor) {
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
