package inc.evil.medassist.appointment.web.validation;

import inc.evil.medassist.appointment.web.CreateAppointmentRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

public class ValidAppointmentTimeConstraintValidator implements ConstraintValidator<ValidAppointmentTime, CreateAppointmentRequest> {
    @Override
    public boolean isValid(CreateAppointmentRequest request, ConstraintValidatorContext context) {
        LocalTime startTime = request.getStartTime();
        LocalTime endTime = request.getEndTime();
        return startTime.isBefore(endTime);
    }
}
