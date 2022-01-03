package inc.evil.medassist.treatment.facade;

import inc.evil.medassist.treatment.web.CreateTreatmentRequest;
import inc.evil.medassist.treatment.web.TreatmentResponse;

import java.util.List;

public interface TreatmentFacade {
    List<TreatmentResponse> findAll();

    TreatmentResponse findById(String id);
    
    void deleteById(String id);

    List<TreatmentResponse> findTreatmentsByDoctorId(String doctorId);

    List<TreatmentResponse> findTreatmentsByPatientId(String patientId);

    List<TreatmentResponse> findTreatmentsByToothId(String toothId);

    TreatmentResponse create(CreateTreatmentRequest request);

}
