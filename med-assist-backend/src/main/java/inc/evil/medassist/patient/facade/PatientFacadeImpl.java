package inc.evil.medassist.patient.facade;

import inc.evil.medassist.patient.model.Patient;
import inc.evil.medassist.patient.service.PatientService;
import inc.evil.medassist.patient.web.UpsertPatientRequest;
import inc.evil.medassist.patient.web.PatientResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PatientFacadeImpl implements PatientFacade {
    private final PatientService patientService;

    public PatientFacadeImpl(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public List<PatientResponse> findAll() {
        return patientService.findAll()
                .stream()
                .map(PatientResponse::from)
                .toList();
    }

    @Override
    public Map<String, Long> countAgeCategories() {
        return patientService.countAgeCategories();
    }

    @Override
    public Map<String, Long> countPatientsCreatedPerMonth() {
        return patientService.countPatientsCreatedPerMonth();
    }

    @Override
    public PatientResponse findById(String id) {
        return PatientResponse.from(patientService.findById(id));
    }

    @Override
    public void deleteById(String id) {
        patientService.deleteById(id);
    }

    @Override
    public PatientResponse create(UpsertPatientRequest request) {
        Patient createdPatient = patientService.create(request.toPatient());
        return PatientResponse.from(createdPatient);
    }

    @Override
    public PatientResponse update(String id, UpsertPatientRequest request) {
        return PatientResponse.from(patientService.update(id, request.toPatient()));
    }
}
