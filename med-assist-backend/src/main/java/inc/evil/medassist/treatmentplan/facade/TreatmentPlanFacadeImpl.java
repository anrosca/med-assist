package inc.evil.medassist.treatmentplan.facade;

import inc.evil.medassist.treatmentplan.service.TreatmentPlanService;
import inc.evil.medassist.treatmentplan.web.TreatmentPlanResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class TreatmentPlanFacadeImpl implements TreatmentPlanFacade {
    private final TreatmentPlanService treatmentPlanService;

    TreatmentPlanFacadeImpl(TreatmentPlanService treatmentPlanService) {
        this.treatmentPlanService = treatmentPlanService;
    }

    @Override
    public List<TreatmentPlanResponse> findAll() {
        return treatmentPlanService.findAll()
                .stream()
                .map(TreatmentPlanResponse::from)
                .toList();
    }

    @Override
    public TreatmentPlanResponse findById(String id) {
        return TreatmentPlanResponse.from(treatmentPlanService.findById(id));
    }

    @Override
    public void deleteById(String id) {
        treatmentPlanService.deleteById(id);
    }
}
