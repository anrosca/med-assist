package inc.evil.medassist.treatment.facade;

import inc.evil.medassist.doctor.service.DoctorService;
import inc.evil.medassist.patient.service.PatientService;
import inc.evil.medassist.teeth.service.ToothService;
import inc.evil.medassist.treatment.model.Treatment;
import inc.evil.medassist.treatment.service.TreatmentService;
import inc.evil.medassist.treatment.web.CreateTreatmentRequest;
import inc.evil.medassist.treatment.web.TreatedTeeth;
import inc.evil.medassist.treatment.web.TreatmentResponse;
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
        toothService.markAsExtracted(getExtractedTeeth(request.getTreatedTeeth()));
        toothService.markAsNotExtracted(getNotExtractedTeeth(request.getTreatedTeeth()));
        return TreatmentResponse.from(createdTreatment);
    }

    private List<String> getNotExtractedTeeth(List<TreatedTeeth> treatedTeeth) {
        return treatedTeeth.stream()
                .filter(t -> !t.isExtracted())
                .map(TreatedTeeth::toothId)
                .toList();
    }

    private Treatment toTreatment(final CreateTreatmentRequest request) {
        return Treatment.builder()
                .description(request.getDescription())
                .doctor(request.getDoctorId() != null ? doctorService.findById(request.getDoctorId()) : null)
                .patient(request.getPatientId() != null ? patientService.findById(request.getPatientId()) : null)
                .teeth(toothService.findAllByIdsIn(getTeethIds(request.getTreatedTeeth())))
                .price(request.getPrice())
                .build();
    }

    private List<String> getExtractedTeeth(List<TreatedTeeth> treatedTeeth) {
        return treatedTeeth.stream()
                .filter(TreatedTeeth::isExtracted)
                .map(TreatedTeeth::toothId)
                .toList();
    }

    private List<String> getTeethIds(List<TreatedTeeth> treatedTeeth) {
        return treatedTeeth.stream()
                .map(TreatedTeeth::toothId)
                .toList();
    }
}
