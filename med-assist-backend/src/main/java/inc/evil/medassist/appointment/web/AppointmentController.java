package inc.evil.medassist.appointment.web;

import inc.evil.medassist.appointment.facade.AppointmentFacade;
import org.springframework.format.annotation.DateTimeFormat;
import inc.evil.medassist.common.validation.ValidationSequence;
import inc.evil.medassist.common.validation.OnUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/count")
    public Long countAll() {
        return appointmentFacade.countAll();
    }

    @GetMapping("/count/operations")
    public Map<String, Long> countOperations() {
        return appointmentFacade.countOperations();
    }

    @GetMapping("/count/per-month")
    public Map<String, Long> countAppointmentsPerMonth() {
        return appointmentFacade.countAppointmentsPerMonth();
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
    public ResponseEntity<?> create(@RequestBody @Validated(ValidationSequence.class) UpsertAppointmentRequest request) {
        AppointmentResponse createdAppointment = appointmentFacade.create(request);
        URI location = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(getClass())
                        .findById(createdAppointment.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location)
                .build();
    }

    @GetMapping(params = "doctorId")
    public List<AppointmentResponse> findDoctorAppointments(@RequestParam String doctorId) {
        return appointmentFacade.findAppointmentsByDoctorId(doctorId);
    }

    @GetMapping(params = {"doctorId", "startDate", "endDate"})
    public List<AppointmentResponse> findDoctorAppointmentsInTimeRange(@RequestParam String doctorId,
                                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate startDate,
                                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate endDate) {
        return appointmentFacade.findAppointmentsByDoctorIdInTimeRange(doctorId, startDate, endDate);
    }

    @PutMapping("{id}")
    public ResponseEntity<AppointmentResponse> update(@PathVariable String id, @RequestBody @Validated({OnUpdate.class, ValidationSequence.After.class}) UpsertAppointmentRequest request) {
        AppointmentResponse updatedAppointment = appointmentFacade.update(id, request);
        return ResponseEntity.ok(updatedAppointment);
    }
}
