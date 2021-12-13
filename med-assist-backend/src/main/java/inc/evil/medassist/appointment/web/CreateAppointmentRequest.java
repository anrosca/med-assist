package inc.evil.medassist.appointment.web;

import inc.evil.medassist.appointment.web.validation.ValidAppointmentSequence;
import inc.evil.medassist.appointment.web.validation.ValidAppointmentTime;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@ValidAppointmentTime(
        groups = ValidAppointmentSequence.Extended.class,
        message = "{create.appointment.end-time.before.start-time}"
)
public class CreateAppointmentRequest {
    @NotNull
    private LocalDate appointmentDate;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    private String operation;

    @NotBlank
    private String doctorId;

    @NotBlank
    private String patientId;

    private String details;

}
