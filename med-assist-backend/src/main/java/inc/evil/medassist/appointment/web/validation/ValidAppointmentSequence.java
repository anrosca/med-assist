package inc.evil.medassist.appointment.web.validation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, ValidAppointmentSequence.Extended.class})
public interface ValidAppointmentSequence {
    interface Extended {}
}
