package inc.evil.medassist.treatmentplan.web;

import inc.evil.medassist.treatmentplan.model.TreatmentPlan;
import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TreatmentPlanResponse {
    private String id;
    private String planName;
    private String patientId;

    public static TreatmentPlanResponse from(TreatmentPlan treatmentPlan) {
        return TreatmentPlanResponse.builder()
                .id(treatmentPlan.getId())
                .planName(treatmentPlan.getPlanName())
                .patientId(treatmentPlan.getPatient().getId())
                .build();
    }
}
