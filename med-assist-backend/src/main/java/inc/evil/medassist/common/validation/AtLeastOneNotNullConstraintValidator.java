package inc.evil.medassist.common.validation;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

@Slf4j
public class AtLeastOneNotNullConstraintValidator implements ConstraintValidator<AtLeastOneNotNull, Object> {

    private String[] fields;

    @Override
    public void initialize(AtLeastOneNotNull constraintAnnotation) {
        fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        boolean atLeasOnNotNull = isAtLeastOneNotNull(object);

        if (!atLeasOnNotNull) {
            for (String field : fields) {
                addConstraintViolations(context, field);
            }
        }

        return atLeasOnNotNull;
    }

    void addConstraintViolations(ConstraintValidatorContext context, String... fields) {
        context.disableDefaultConstraintViolation();
        ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder =
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate());

        for (String field : fields) {
            constraintViolationBuilder.addPropertyNode(field).addConstraintViolation();
        }
    }

    public boolean isAtLeastOneNotNull(Object object) {
        boolean isAtLeastOneNotNull = false;
        for (String field : fields) {
            if (getFieldValue(object, field) != null) {
                isAtLeastOneNotNull = true;
                break;
            }
        }

        return isAtLeastOneNotNull;
    }

    private Object getFieldValue(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("Couldn't get field value", e);
        }
        return null;
    }

}
