package inc.evil.medassist.appointment.web;

import inc.evil.medassist.common.validation.ValidationSequence;
import inc.evil.medassist.appointment.web.validation.ValidAppointmentTime;
import inc.evil.medassist.common.validation.OnCreate;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@ValidAppointmentTime(
        groups = ValidationSequence.After.class,
        message = "{create.appointment.end-time.before.start-time}"
)
public class UpsertAppointmentRequest {
    @NotNull(groups = OnCreate.class)
    private LocalDate appointmentDate;

    @NotNull(groups = OnCreate.class)
    private LocalTime startTime;

    @NotNull(groups = OnCreate.class)
    private LocalTime endTime;

    private String operation;

    @NotBlank(groups = OnCreate.class)
    private String doctorId;

    @NotBlank(groups = OnCreate.class)
    private String patientId;

    private String details;

}
