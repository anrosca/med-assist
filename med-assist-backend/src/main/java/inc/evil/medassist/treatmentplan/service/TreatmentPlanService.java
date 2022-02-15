package inc.evil.medassist.treatmentplan.service;

import inc.evil.medassist.treatmentplan.model.TreatmentPlan;

import java.util.List;

public interface TreatmentPlanService {
    List<TreatmentPlan> findAll();

    TreatmentPlan findById(String id);

    void deleteById(String id);
}
