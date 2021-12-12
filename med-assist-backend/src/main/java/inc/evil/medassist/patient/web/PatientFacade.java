package inc.evil.medassist.patient.web;

import inc.evil.medassist.patient.model.Patient;
import inc.evil.medassist.patient.service.PatientService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientFacade {
    private final PatientService patientService;

    public PatientFacade(PatientService patientService) {
        this.patientService = patientService;
    }

    public List<PatientResponse> findAll() {
        return patientService.findAll()
                .stream()
                .map(PatientResponse::from)
                .toList();
    }

    public PatientResponse findById(String id) {
        return PatientResponse.from(patientService.findById(id));
    }

    public void deleteById(String id) {
        patientService.deleteById(id);
    }

    public PatientResponse create(CreatePatientRequest request) {
        Patient createdPatient = patientService.create(request.toPatient());
        return PatientResponse.from(createdPatient);
    }
}
