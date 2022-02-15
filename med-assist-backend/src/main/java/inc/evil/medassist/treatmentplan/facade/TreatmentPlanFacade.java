package inc.evil.medassist.treatmentplan.facade;

import inc.evil.medassist.treatmentplan.web.TreatmentPlanResponse;

import java.util.List;

public interface TreatmentPlanFacade {
    List<TreatmentPlanResponse> findAll();

    TreatmentPlanResponse findById(String id);

    void deleteById(String id);
}
