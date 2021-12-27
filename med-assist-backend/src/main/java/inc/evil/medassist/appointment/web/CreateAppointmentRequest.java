package inc.evil.medassist.appointment.web;

import inc.evil.medassist.appointment.web.validation.ValidAppointmentSequence;
import inc.evil.medassist.appointment.web.validation.ValidAppointmentTime;
import inc.evil.medassist.common.validation.AtLeastOneNotNull;
import inc.evil.medassist.patient.web.CreatePatientRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ValidAppointmentTime(
        groups = ValidAppointmentSequence.Extended.class,
        message = "{create.appointment.end-time.before.start-time}"
)
@AtLeastOneNotNull(fields = {"patientId", "patientRequest"})
public class CreateAppointmentRequest {

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    private String operation;

    @NotBlank
    private String doctorId;

    private boolean existingPatient;

    private CreatePatientRequest patientRequest;

    private String patientId;

    private String details;

}
