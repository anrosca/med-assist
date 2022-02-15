package inc.evil.medassist.treatmentplan.service;

import inc.evil.medassist.common.exception.NotFoundException;
import inc.evil.medassist.treatmentplan.model.TreatmentPlan;
import inc.evil.medassist.treatmentplan.repository.TreatmentPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class TreatmentPlanServiceImpl implements TreatmentPlanService {
    private final TreatmentPlanRepository treatmentPlanRepository;

    TreatmentPlanServiceImpl(TreatmentPlanRepository treatmentPlanRepository) {
        this.treatmentPlanRepository = treatmentPlanRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TreatmentPlan> findAll() {
        return treatmentPlanRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TreatmentPlan findById(String id) {
        return treatmentPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(TreatmentPlan.class, "id", id));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        TreatmentPlan treatmentPlanToDelete = findById(id);
        treatmentPlanRepository.delete(treatmentPlanToDelete);
    }
}
