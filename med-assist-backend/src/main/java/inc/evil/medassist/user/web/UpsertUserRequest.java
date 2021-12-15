package inc.evil.medassist.user.web;

import inc.evil.medassist.common.validation.OnCreate;
import inc.evil.medassist.user.model.Authority;
import inc.evil.medassist.user.model.User;
import inc.evil.medassist.user.model.UserAuthority;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class UpsertUserRequest {
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
    @NotEmpty(groups = {OnCreate.class})
    private Set<String> authorities;
    private boolean enabled = true;

    public User toUser() {

        User user = User.builder()
                .username(this.getUsername())
                .email(this.getEmail())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .password(this.getPassword())
                .enabled(this.isEnabled())
                .build();

        if (this.getAuthorities() != null)
            this.getAuthorities()
                    .stream()
                    .map(a -> new UserAuthority(Authority.valueOf(a)))
                    .forEach(user::addAuthority);

        return user;
    }
}
