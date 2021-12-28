package inc.evil.medassist.common.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AtLeastOneNotNullConstraintValidatorTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void isValid_whenOneFieldIsNotNull_isTrue() {
        ObjectToValidate object = new ObjectToValidate("field1", null);

        Set<ConstraintViolation<ObjectToValidate>> actualViolations = validator.validate(object);

        assertThat(actualViolations).isEmpty();
    }

    @Test
    public void isValid_whenOtherFieldIsNotNull_isTrue() {
        ObjectToValidate object = new ObjectToValidate(null, "field2");

        Set<ConstraintViolation<ObjectToValidate>> actualViolations = validator.validate(object);

        assertThat(actualViolations).isEmpty();
    }

    @Test
    public void isValid_whenBothFieldsNotNull_isTrue() {
        ObjectToValidate object = new ObjectToValidate("field1", "field2");

        Set<ConstraintViolation<ObjectToValidate>> actualViolations = validator.validate(object);

        assertThat(actualViolations).isEmpty();
    }

    @Test
    public void isValid_whenBothFieldsNull_addsConstraintViolations() {
        ObjectToValidate object = new ObjectToValidate(null, null);

        Set<ConstraintViolation<ObjectToValidate>> violations = validator.validate(object);

        List<Violation> actualViolations = violations.stream()
                .map(v -> new Violation(v.getMessage(), v.getPropertyPath().toString()))
                .toList();
        Violation[] expectedViolations = {
                new Violation("at least should not be null", "field1"),
                new Violation("at least should not be null", "field2")
        };
        assertThat(actualViolations).containsExactlyInAnyOrder(expectedViolations);
    }

    record Violation(String message, String path) {}

    @AtLeastOneNotNull(fields = {"field1", "field2"})
    record ObjectToValidate(String field1, String field2) { }
}

