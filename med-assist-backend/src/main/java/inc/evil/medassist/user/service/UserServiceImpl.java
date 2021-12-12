package inc.evil.medassist.user.service;

import inc.evil.medassist.common.exception.NotFoundException;
import inc.evil.medassist.user.model.Authority;
import inc.evil.medassist.user.model.UserAuthority;
import inc.evil.medassist.user.repository.UserRepository;
import inc.evil.medassist.user.web.UpsertUserRequest;
import inc.evil.medassist.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(final String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, "id", id));
    }

    @Override
    @Transactional
    public User create(final UpsertUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }
        if (request.getAuthorities() == null) {
            request.setAuthorities(new HashSet<>());
        }



        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        request.getAuthorities()
                .stream()
                .map(a -> new UserAuthority(Authority.valueOf(a)))
                .forEach(user::addAuthority);

        userRepository.save(user);

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(final String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(User.class, "username", username));
    }

    @Override
    @Transactional
    public void delete(final String id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User update(final String id, final UpsertUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, "id", id));
        if (request.getFirstName() != null)
            user.setFirstName(request.getFirstName());
        if (request.getLastName() != null)
            user.setLastName(request.getLastName());
        if (request.getEmail() != null)
            user.setEmail(request.getEmail());
        if (request.getUsername() != null)
            user.setUsername(request.getUsername());
        if (request.getAuthorities() != null)
            request.getAuthorities()
                    .stream()
                    .map(a -> new UserAuthority(Authority.valueOf(a)))
                    .forEach(user::addAuthority);
        if (request.getPassword() != null)
            user.setPassword(request.getPassword());
        return user;
    }
}
