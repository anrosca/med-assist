package inc.evil.medassist.user.web;

import inc.evil.medassist.user.model.User;
import org.springframework.transaction.annotation.Transactional;

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
