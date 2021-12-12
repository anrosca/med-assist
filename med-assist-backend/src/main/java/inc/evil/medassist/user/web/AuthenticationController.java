package inc.evil.medassist.user.web;


import inc.evil.medassist.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserFacade userFacade;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody @Valid AuthRequest request) {
        final UserResponse userView = userFacade.authenticate(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userView.getAccessToken())
                .body(userView);
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody UpsertUserRequest request) {
        return userFacade.create(request);
    }

}
