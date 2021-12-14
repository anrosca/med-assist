package inc.evil.medassist.user.facade;

import inc.evil.medassist.user.web.AuthRequest;
import inc.evil.medassist.user.web.UpsertUserRequest;
import inc.evil.medassist.user.web.UserResponse;

import java.util.List;

public interface UserFacade {
    List<UserResponse> findAll();

    UserResponse findById(String id);

    UserResponse create(UpsertUserRequest upsertUserRequest);

    UserResponse findByUsername(String username);

    void deleteById(String id);

    UserResponse update(String id, UpsertUserRequest request);

    UserResponse authenticate(AuthRequest authRequest);
}
