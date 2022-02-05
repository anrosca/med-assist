package inc.evil.medassist.patient.facade;

import inc.evil.medassist.patient.web.UpsertPatientRequest;
import inc.evil.medassist.patient.web.PatientResponse;

import java.util.List;
import java.util.Map;

public interface PatientFacade {
    List<PatientResponse> findAll();

    Map<String, Long> countAgeCategories();

    Map<String, Long> countPatientsCreatedPerMonth();

    PatientResponse findById(String id);

    void deleteById(String id);

    PatientResponse create(UpsertPatientRequest request);

    PatientResponse update(String id, UpsertPatientRequest request);
}
