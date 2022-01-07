package inc.evil.medassist.doctor.web;

import inc.evil.medassist.common.validation.OnCreate;
import inc.evil.medassist.common.validation.OnUpdate;
import inc.evil.medassist.doctor.facade.DoctorFacade;
import inc.evil.medassist.user.model.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorFacade doctorFacade;

    @GetMapping
    public List<DoctorResponse> findAll() {
        return doctorFacade.findAll();
    }

    @GetMapping("/count/per-specialty")
    public Map<String, Long> countSpecialties() {
        return doctorFacade.countSpecialties();
    }

    @GetMapping("{id}")
    public DoctorResponse findById(@PathVariable String id) {
        return doctorFacade.findById(id);
    }

    @DeleteMapping("{id}")
    @RolesAllowed(Authority.Fields.POWER_USER)
    public void deleteById(@PathVariable String id) {
        doctorFacade.deleteById(id);
    }

    @PostMapping
    @RolesAllowed(Authority.Fields.POWER_USER)
    public ResponseEntity<?> create(@RequestBody @Validated(OnCreate.class) UpsertDoctorRequest request) {
        DoctorResponse createdDoctor = doctorFacade.create(request);
        URI location = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(getClass())
                        .findById(createdDoctor.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location).body(createdDoctor);
    }

    @PutMapping("{id}")
    public ResponseEntity<DoctorResponse> update(@PathVariable String id,
                                                 @RequestBody @Validated(OnUpdate.class) UpsertDoctorRequest request) {
        final DoctorResponse doctorResponse = doctorFacade.update(id, request);
        return ResponseEntity.ok().body(doctorResponse);
    }
}
