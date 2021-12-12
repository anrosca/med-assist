package inc.evil.medassist.user.web;

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
    @NotBlank(message = "{first.name.required}")
    private String firstName;
    @NotBlank(message = "{last.name.required}")
    private String lastName;
    @NotBlank(message = "{username.required}")
    private String username;
    @NotBlank(message = "{email.required}")
    @Email(message = "{email.invalid.format}")
    private String email;
    @NotBlank(message = "{password.required}")
    private String password;
    @NotEmpty(message = "{authorities.not.empty}")
    private Set<String> authorities;

    public User toUser(){

        User user = User.builder()
                .username(this.getUsername())
                .email(this.getEmail())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .password(this.getPassword())
                .build();

        this.getAuthorities()
                .stream()
                .map(a -> new UserAuthority(Authority.valueOf(a)))
                .forEach(user::addAuthority);

        return user;

    }
}
