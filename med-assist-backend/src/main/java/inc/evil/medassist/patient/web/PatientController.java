package inc.evil.medassist.patient.web;

import inc.evil.medassist.common.validation.OnCreate;
import inc.evil.medassist.common.validation.OnUpdate;
import inc.evil.medassist.patient.facade.PatientFacade;
import inc.evil.medassist.user.model.Authority;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/count/per-age-category")
    public Map<String, Long> countAgeCategories() {
        return patientFacade.countAgeCategories();
    }

    @GetMapping("/count/per-month")
    public Map<String, Long> countPatientsCreatedPerMonth() {
        return patientFacade.countPatientsCreatedPerMonth();
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

    @PutMapping("{id}")
    public ResponseEntity<PatientResponse> update(@PathVariable String id,
                                                 @RequestBody @Validated(OnUpdate.class) UpsertPatientRequest request) {
        final PatientResponse patientResponse = patientFacade.update(id, request);
        return ResponseEntity.ok(patientResponse);
    }

    @PostMapping
    @RolesAllowed(Authority.Fields.POWER_USER)
    public ResponseEntity<?> create(@Validated(OnCreate.class) @RequestBody UpsertPatientRequest request) {
        PatientResponse createdPatient = patientFacade.create(request);
        URI location = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(getClass())
                        .findById(createdPatient.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location)
                .build();
    }
}
