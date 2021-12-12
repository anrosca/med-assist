package inc.evil.medassist.patient.web;

import inc.evil.medassist.user.model.Authority;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {
    private final PatientFacade patientFacade;

    public PatientController(PatientFacade patientFacade) {
        this.patientFacade = patientFacade;
    }

    @GetMapping
    public List<PatientResponse> findAll() {
        return patientFacade.findAll();
    }

    @GetMapping("{id}")
    public PatientResponse findById(@PathVariable String id) {
        return patientFacade.findById(id);
    }

    @DeleteMapping("{id}")
    @RolesAllowed(Authority.Fields.POWER_USER)
    public void deleteById(@PathVariable String id) {
        patientFacade.deleteById(id);
    }

    @PostMapping
    @RolesAllowed(Authority.Fields.POWER_USER)
    public ResponseEntity<?> create(@Valid @RequestBody CreatePatientRequest request) {
        PatientResponse createdPatient = patientFacade.create(request);
        URI location = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(getClass())
                        .findById(createdPatient.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location)
                .build();
    }
}
