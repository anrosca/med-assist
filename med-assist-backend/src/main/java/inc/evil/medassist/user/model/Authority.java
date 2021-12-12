package inc.evil.medassist.user.model;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum Authority {
    @FieldNameConstants.Include DOCTOR,
    @FieldNameConstants.Include POWER_USER;
}
