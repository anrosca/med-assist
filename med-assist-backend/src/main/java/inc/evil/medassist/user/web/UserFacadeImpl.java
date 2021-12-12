package inc.evil.medassist.user.web;

import inc.evil.medassist.config.security.JwtTokenManager;
import inc.evil.medassist.user.model.User;
import inc.evil.medassist.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class UserFacadeImpl implements UserFacade {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenManager jwtTokenManager;
    private final UserService userService;

    @Override
    @Transactional
    public UserResponse authenticate(final AuthRequest authRequest) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        User user = (User) authentication.getPrincipal();
        final String accessToken = jwtTokenManager.generateAccessToken(user);
        final UserResponse userResponse = UserResponse.from(user);
        userResponse.setAccessToken(accessToken);
        return userResponse;
    }

    @Override
    public List<UserResponse> findAll() {
        return userService.findAll().stream().map(UserResponse::from).collect(Collectors.toList());
    }

    @Override
    public UserResponse findById(final String id) {
        return UserResponse.from(userService.findById(id));
    }

    @Override
    public UserResponse create(final UpsertUserRequest upsertUserRequest) {
        return UserResponse.from(userService.create(upsertUserRequest));
    }

    @Override
    public UserResponse findByUsername(final String username) {
        return UserResponse.from(userService.findByUsername(username));
    }

    @Override
    public void delete(final String id) {
        userService.delete(id);
    }

    @Override
    public UserResponse update(final String id, final UpsertUserRequest request) {
        return UserResponse.from(userService.update(id, request));
    }
}
