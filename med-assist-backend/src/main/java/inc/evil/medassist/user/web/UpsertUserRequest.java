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
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotEmpty
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
