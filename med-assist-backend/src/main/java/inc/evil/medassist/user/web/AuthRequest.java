package inc.evil.medassist.user.web;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class AuthRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
