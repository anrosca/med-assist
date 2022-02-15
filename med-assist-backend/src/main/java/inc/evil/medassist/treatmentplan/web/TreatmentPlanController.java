package inc.evil.medassist.treatmentplan.web;

import inc.evil.medassist.treatmentplan.facade.TreatmentPlanFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/treatment-plans")
public class TreatmentPlanController {
    private final TreatmentPlanFacade treatmentPlanFacade;

    public TreatmentPlanController(TreatmentPlanFacade treatmentPlanFacade) {
        this.treatmentPlanFacade = treatmentPlanFacade;
    }

    @GetMapping
    public List<TreatmentPlanResponse> findAll() {
        return treatmentPlanFacade.findAll();
    }

    @GetMapping("{id}")
    public TreatmentPlanResponse findById(@PathVariable("id") String id) {
        return treatmentPlanFacade.findById(id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") String id) {
        treatmentPlanFacade.deleteById(id);
    }
}
