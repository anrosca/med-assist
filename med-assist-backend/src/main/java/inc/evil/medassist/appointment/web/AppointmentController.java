package inc.evil.medassist.appointment.web;

import inc.evil.medassist.appointment.facade.AppointmentFacade;
import inc.evil.medassist.appointment.web.validation.ValidAppointmentSequence;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {
    private final AppointmentFacade appointmentFacade;

    public AppointmentController(AppointmentFacade appointmentFacade) {
        this.appointmentFacade = appointmentFacade;
    }

    @GetMapping
    public List<AppointmentResponse> findAll() {
        return appointmentFacade.findAll();
    }

    @GetMapping("{id}")
    public AppointmentResponse findById(@PathVariable String id) {
        return appointmentFacade.findById(id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        appointmentFacade.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated(ValidAppointmentSequence.class) CreateAppointmentRequest request) {
        AppointmentResponse createdAppointment = appointmentFacade.create(request);
        URI location = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(getClass())
                        .findById(createdAppointment.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location)
                .build();
    }
}
