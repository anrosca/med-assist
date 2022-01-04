package inc.evil.medassist.treatment.facade;

import inc.evil.medassist.teeth.service.ToothService;
import inc.evil.medassist.treatment.web.CreateTreatmentRequest;
import inc.evil.medassist.treatment.web.TreatmentResponse;
import inc.evil.medassist.doctor.service.DoctorService;
import inc.evil.medassist.patient.service.PatientService;
import inc.evil.medassist.treatment.model.Treatment;
import inc.evil.medassist.treatment.service.TreatmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
class TreatmentFacadeImpl implements TreatmentFacade {
    private final TreatmentService treatmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final ToothService toothService;

    @Override
    public List<TreatmentResponse> findAll() {
        return treatmentService.findAll()
                .stream()
                .map(TreatmentResponse::from)
                .toList();
    }

    @Override
    public TreatmentResponse findById(String id) {
        return TreatmentResponse.from(treatmentService.findById(id));
    }

    @Override
    public void deleteById(String id) {
        treatmentService.deleteById(id);
    }

    @Override
    public List<TreatmentResponse> findTreatmentsByDoctorId(String doctorId) {
        return treatmentService.findByDoctorId(doctorId)
                .stream()
                .map(TreatmentResponse::from)
                .toList();
    }

    @Override
    public List<TreatmentResponse> findTreatmentsByPatientId(String patientId) {
        return treatmentService.findByPatientId(patientId)
                .stream()
                .map(TreatmentResponse::from)
                .toList();
    }

    @Override
    public List<TreatmentResponse> findTreatmentsByToothId(String toothId) {
        return treatmentService.findByToothId(toothId)
                .stream()
                .map(TreatmentResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public TreatmentResponse create(CreateTreatmentRequest request) {
        Treatment appointmentToCreate = toTreatment(request);
        Treatment createdTreatment = treatmentService.create(appointmentToCreate);
        return TreatmentResponse.from(createdTreatment);
    }

    private Treatment toTreatment(final CreateTreatmentRequest request) {
        return Treatment.builder()
                .description(request.getDescription())
                .doctor(request.getDoctorId() != null ? doctorService.findById(request.getDoctorId()) : null)
                .patient(request.getPatientId() != null ? patientService.findById(request.getPatientId()) : null)
                .teeth(toothService.findAllByIdsIn(request.getTeethIds()))
                .price(request.getPrice())
                .build();
    }

}
