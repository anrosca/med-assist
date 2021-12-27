package inc.evil.medassist.appointment.web.validation;

import inc.evil.medassist.appointment.web.UpsertAppointmentRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

public class ValidAppointmentTimeConstraintValidator implements ConstraintValidator<ValidAppointmentTime, UpsertAppointmentRequest> {
    @Override
    public boolean isValid(UpsertAppointmentRequest request, ConstraintValidatorContext context) {
        LocalTime startTime = request.getStartDate().toLocalTime();
        LocalTime endTime = request.getEndDate().toLocalTime();
        if (startTime == null || endTime == null)
            return true;
        return startTime.isBefore(endTime);
    }
}
