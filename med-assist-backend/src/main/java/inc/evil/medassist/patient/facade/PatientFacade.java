package inc.evil.medassist.patient.facade;

import inc.evil.medassist.patient.web.CreatePatientRequest;
import inc.evil.medassist.patient.web.PatientResponse;

import java.util.List;

public interface PatientFacade {
    List<PatientResponse> findAll();

    PatientResponse findById(String id);

    void deleteById(String id);

    PatientResponse create(CreatePatientRequest request);
}
