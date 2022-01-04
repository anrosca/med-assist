package inc.evil.medassist.treatment.service;


import inc.evil.medassist.common.exception.NotFoundException;
import inc.evil.medassist.doctor.repository.DoctorRepository;
import inc.evil.medassist.patient.repository.PatientRepository;
import inc.evil.medassist.treatment.model.Treatment;
import inc.evil.medassist.treatment.repository.TreatmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class TreatmentServiceImpl implements TreatmentService {
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final TreatmentRepository treatmentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Treatment> findAll() {
        return treatmentRepository.findTreatmentsWithDoctorsAndPatientsAndTeeth();
    }

    @Override
    @Transactional(readOnly = true)
    public Treatment findById(String id) {
        return treatmentRepository.findByIdWithDoctorsAndPatientsAndTeeth(id)
                .orElseThrow(() -> new NotFoundException(Treatment.class, "id", id));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Treatment treatmentToDelete = findById(id);
        treatmentRepository.delete(treatmentToDelete);
    }

    @Override
    @Transactional
    public Treatment create(Treatment treatmentToCreate) {
        return treatmentRepository.save(treatmentToCreate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Treatment> findByDoctorId(String id) {
        return treatmentRepository.findByDoctorIdWithDoctorsAndPatientsAndTeeth(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Treatment> findByPatientId(final String id) {
        return treatmentRepository.findByPatientIdWithDoctorsAndPatientsAndTeeth(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Treatment> findByToothId(final String toothId) {
        return treatmentRepository.findByToothIdWithDoctorsAndPatientsAndTeeth(toothId);
    }

}
