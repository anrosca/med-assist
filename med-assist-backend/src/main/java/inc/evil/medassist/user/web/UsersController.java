package inc.evil.medassist.user.web;

import inc.evil.medassist.user.model.Authority;
import inc.evil.medassist.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RolesAllowed(Authority.Fields.POWER_USER)
public class UsersController {
    private final UserFacade userFacade;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UpsertUserRequest request) {
        final UserResponse userResponse = userFacade.create(request);
        URI location = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(getClass())
                                                                      .get(userResponse.getId())).build().toUri();
        return ResponseEntity.created(location).body(userResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponse> update(@PathVariable String id, @RequestBody UpsertUserRequest request) {
        final UserResponse userResponse = userFacade.update(id, request);
        return ResponseEntity.ok().body(userResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userFacade.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> get(@PathVariable String id) {
        return ResponseEntity.ok().body(userFacade.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok().body(userFacade.findAll());
    }

}
