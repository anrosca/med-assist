package inc.evil.medassist.treatment.web;

import inc.evil.medassist.common.validation.OnUpdate;
import inc.evil.medassist.common.validation.ValidationSequence;
import inc.evil.medassist.treatment.facade.TreatmentFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/treatments")
public class TreatmentController {
    private final TreatmentFacade treatmentFacade;

    @GetMapping
    public List<TreatmentResponse> findAll() {
        return treatmentFacade.findAll();
    }

    @GetMapping("{id}")
    public TreatmentResponse findById(@PathVariable String id) {
        return treatmentFacade.findById(id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        treatmentFacade.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateTreatmentRequest request) {
        TreatmentResponse createdTreatment = treatmentFacade.create(request);
        URI location = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(getClass())
                        .findById(createdTreatment.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(params = "doctorId")
    public List<TreatmentResponse> findTreatmentsByDoctorId(@RequestParam String doctorId) {
        return treatmentFacade.findTreatmentsByDoctorId(doctorId);
    }

    @GetMapping(params = "patientId")
    public List<TreatmentResponse> findTreatmentsByPatientId(@RequestParam String patientId) {
        return treatmentFacade.findTreatmentsByPatientId(patientId);
    }

    @GetMapping(params = "toothId")
    public List<TreatmentResponse> findTreatmentsByToothId(@RequestParam String toothId) {
        return treatmentFacade.findTreatmentsByToothId(toothId);
    }

}
