package inc.evil.medassist.patient.facade;

import inc.evil.medassist.patient.model.Patient;
import inc.evil.medassist.patient.service.PatientService;
import inc.evil.medassist.patient.web.CreatePatientRequest;
import inc.evil.medassist.patient.web.PatientResponse;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public PatientResponse findById(String id) {
        return PatientResponse.from(patientService.findById(id));
    }

    @Override
    public void deleteById(String id) {
        patientService.deleteById(id);
    }

    @Override
    public PatientResponse create(CreatePatientRequest request) {
        Patient createdPatient = patientService.create(request.toPatient());
        return PatientResponse.from(createdPatient);
    }
}
