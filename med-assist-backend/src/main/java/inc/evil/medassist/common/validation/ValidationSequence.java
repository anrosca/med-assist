package inc.evil.medassist.common.validation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, OnCreate.class, OnUpdate.class, ValidationSequence.After.class})
public interface ValidationSequence {
    interface After {}
}
