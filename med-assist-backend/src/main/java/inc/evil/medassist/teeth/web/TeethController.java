package inc.evil.medassist.teeth.web;

import inc.evil.medassist.teeth.facade.ToothFacade;
import inc.evil.medassist.treatment.facade.TreatmentFacade;
import inc.evil.medassist.treatment.web.CreateTreatmentRequest;
import inc.evil.medassist.treatment.web.TreatmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teeth")
public class TeethController {
    private final ToothFacade toothFacade;

    @GetMapping("{id}")
    public ToothResponse findById(@PathVariable String id) {
        return toothFacade.findById(id);
    }

    @GetMapping(params = "patientId")
    public List<ToothResponse> findTeethByPatientId(@RequestParam String patientId) {
        return toothFacade.findAllByPatientId(patientId);
    }
}
