package inc.evil.medassist.common.validation;

import inc.evil.medassist.appointment.web.validation.ValidAppointmentTimeConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = AtLeastOneNotNullConstraintValidator.class)
public @interface AtLeastOneNotNull {
    String message() default "{inc.evil.medassist.AtLeastOneNotNull}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] fields();
}
