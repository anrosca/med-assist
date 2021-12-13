package inc.evil.medassist.appointment.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = ValidAppointmentTimeConstraintValidator.class)
public @interface ValidAppointmentTime {
    String message() default "{inc.evil.medassist.appointment.ValidAppointmentTime}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
