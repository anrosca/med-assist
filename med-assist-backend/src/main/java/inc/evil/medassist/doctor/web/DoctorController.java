package inc.evil.medassist.doctor.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {
    private final DoctorFacade doctorFacade;

    public DoctorController(DoctorFacade doctorFacade) {
        this.doctorFacade = doctorFacade;
    }

    @GetMapping
    public List<DoctorResponse> findAll() {
        return doctorFacade.findAll();
    }

    @GetMapping("{id}")
    public DoctorResponse findById(@PathVariable String id) {
        return doctorFacade.findById(id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        doctorFacade.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateDoctorRequest request) {
        DoctorResponse createdDoctor = doctorFacade.create(request);
        URI location = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(getClass())
                        .findById(createdDoctor.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location)
                .build();
    }
}
