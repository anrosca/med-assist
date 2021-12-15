package inc.evil.medassist.doctor.web;

import inc.evil.medassist.common.validation.OnCreate;
import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.doctor.model.Specialty;
import inc.evil.medassist.user.model.Authority;
import inc.evil.medassist.user.model.User;
import inc.evil.medassist.user.model.UserAuthority;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class UpsertDoctorRequest {
    @NotBlank(groups = {OnCreate.class})
    private String firstName;
    @NotBlank(groups = {OnCreate.class})
    private String lastName;
    @NotBlank(groups = {OnCreate.class})
    private String username;
    @NotBlank(groups = {OnCreate.class})
    @Email(groups = {OnCreate.class})
    private String email;
    @NotBlank(groups = {OnCreate.class})
    private String password;
    @NotNull(groups = {OnCreate.class})
    private Specialty specialty;
    @NotBlank(groups = {OnCreate.class})
    private String telephoneNumber;
    @NotEmpty(groups = {OnCreate.class})
    private Set<String> authorities;
    private boolean enabled = true;

    public Doctor toDoctor() {

        Doctor doctor = Doctor.builder()
                .username(this.getUsername())
                .email(this.getEmail())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .password(this.getPassword())
                .telephoneNumber(this.getTelephoneNumber())
                .specialty(this.getSpecialty())
                .enabled(this.isEnabled())
                .build();

        if (this.getAuthorities() != null)
            this.getAuthorities()
                    .stream()
                    .map(a -> new UserAuthority(Authority.valueOf(a)))
                    .forEach(doctor::addAuthority);

        return doctor;
    }
}
