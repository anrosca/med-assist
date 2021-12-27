package inc.evil.medassist.common.validation;

import lombok.Data;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AtLeastOneNotNullConstraintValidatorTest {
    @Spy
    private AtLeastOneNotNullConstraintValidator atLeastOneNotNullConstraintValidator;

    @Mock
    private ConstraintValidatorContext context;
    @Mock
    private ObjectToValidate object;
    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;
    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext noteBuilderDefineContext;
    @Mock
    private AtLeastOneNotNull atLeastOneNotNull;

    @Before
    public void setUp() {
        atLeastOneNotNullConstraintValidator = new AtLeastOneNotNullConstraintValidator();

        object = new ObjectToValidate();

        when(context.getDefaultConstraintMessageTemplate()).thenReturn("");
        when(context.buildConstraintViolationWithTemplate("")).thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addPropertyNode(anyString())).thenReturn(noteBuilderDefineContext);
        when(noteBuilderDefineContext.addConstraintViolation()).thenReturn(context);
    }

    @Test
    public void isValid_whenBothFieldsNull_isFalse() {
        String[] fields = {"field1", "field2"};
        when(atLeastOneNotNull.fields()).thenReturn(fields);
        atLeastOneNotNullConstraintValidator.initialize(atLeastOneNotNull);
        assertFalse(atLeastOneNotNullConstraintValidator.isValid(object, context));
    }

    @Test
    public void isValid_whenOneFieldIsNotNull_isTrue() {
        String[] fields = {"field1", "field2"};
        object.setField1("field1");
        when(atLeastOneNotNull.fields()).thenReturn(fields);
        atLeastOneNotNullConstraintValidator.initialize(atLeastOneNotNull);
        assertTrue(atLeastOneNotNullConstraintValidator.isValid(object, context));
    }

    @Test
    public void isValid_whenOtherFieldIsNotNull_isTrue() {
        String[] fields = {"field1", "field2"};
        object.setField2("field2");
        when(atLeastOneNotNull.fields()).thenReturn(fields);
        atLeastOneNotNullConstraintValidator.initialize(atLeastOneNotNull);
        assertTrue(atLeastOneNotNullConstraintValidator.isValid(object, context));
    }

    @Test
    public void isValid_whenBothFieldsNotNull_isTrue() {
        String[] fields = {"field1", "field2"};
        object.setField2("field2");
        object.setField1("field1");
        when(atLeastOneNotNull.fields()).thenReturn(fields);
        atLeastOneNotNullConstraintValidator.initialize(atLeastOneNotNull);
        assertTrue(atLeastOneNotNullConstraintValidator.isValid(object, context));
    }

    @Test
    public void isValid_whenBothFieldsNull_addsConstraintViolations() {
        String[] fields = {"field1", "field2"};
        when(atLeastOneNotNull.fields()).thenReturn(fields);
        atLeastOneNotNullConstraintValidator.initialize(atLeastOneNotNull);
        atLeastOneNotNullConstraintValidator.isValid(object, context);
        verify(constraintViolationBuilder).addPropertyNode("field1");
        verify(constraintViolationBuilder).addPropertyNode("field2");
    }

    @Data
    class ObjectToValidate {
        private String field1;
        private String field2;
    }
}
