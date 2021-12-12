package inc.evil.medassist.user.service;

import inc.evil.medassist.user.model.User;
import inc.evil.medassist.user.web.AuthRequest;
import inc.evil.medassist.user.web.UpsertUserRequest;
import inc.evil.medassist.user.web.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(String id);

    User create(User newUser);

    User findByUsername(String username);

    void delete(String id);

    User update(String id, User request);
}
