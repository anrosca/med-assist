package inc.evil.medassist.appointment.web;

import inc.evil.medassist.common.validation.ValidationSequence;
import inc.evil.medassist.appointment.web.validation.ValidAppointmentTime;
import inc.evil.medassist.common.validation.AtLeastOneNotNull;
import inc.evil.medassist.patient.web.CreatePatientRequest;
import inc.evil.medassist.common.validation.OnCreate;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ValidAppointmentTime(
        groups = ValidationSequence.After.class,
        message = "{create.appointment.end-time.before.start-time}"
)
@AtLeastOneNotNull(fields = {"patientId", "patientRequest"})
public class UpsertAppointmentRequest {

    @NotNull(groups = OnCreate.class)
    private LocalDateTime startDate;

    @NotNull(groups = OnCreate.class)
    private LocalDateTime endDate;

    private String operation;

    @NotBlank(groups = OnCreate.class)
    private String doctorId;

    @NotBlank(groups = OnCreate.class)
    private String patientId;

    private boolean existingPatient;

    private CreatePatientRequest patientRequest;

    private String details;

}
