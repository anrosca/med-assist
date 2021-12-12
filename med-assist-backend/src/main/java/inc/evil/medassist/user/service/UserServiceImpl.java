package inc.evil.medassist.user.service;

import inc.evil.medassist.common.exception.NotFoundException;
import inc.evil.medassist.user.model.Authority;
import inc.evil.medassist.user.model.UserAuthority;
import inc.evil.medassist.user.repository.UserRepository;
import inc.evil.medassist.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    public User create(final User newUser) {
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(final String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException(User.class, "username", username));
    }

    @Override
    @Transactional
    public void deleteById(final String id) {
        final User toDelete = findById(id);
        userRepository.delete(toDelete);
    }

    @Override
    @Transactional
    public User update(final String id, final User newUserState) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, "id", id));
        if (newUserState.getFirstName() != null)
            user.setFirstName(newUserState.getFirstName());
        if (newUserState.getLastName() != null)
            user.setLastName(newUserState.getLastName());
        if (newUserState.getEmail() != null)
            user.setEmail(newUserState.getEmail());
        if (newUserState.getUsername() != null)
            user.setUsername(newUserState.getUsername());
        if (!CollectionUtils.isEmpty(newUserState.getAuthorities())) {
            user.getAuthorities().clear();
            newUserState.getAuthorities()
                    .forEach(user::addAuthority);
        }
        if (newUserState.getPassword() != null)
            user.setPassword(newUserState.getPassword());
        return user;
    }
}
