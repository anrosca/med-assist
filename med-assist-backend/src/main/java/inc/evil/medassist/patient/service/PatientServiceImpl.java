package inc.evil.medassist.patient.service;

import inc.evil.medassist.common.exception.NotFoundException;
import inc.evil.medassist.patient.model.Patient;
import inc.evil.medassist.patient.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Patient> findAll() {
        log.debug("Finding all patients");
        return patientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Patient findById(String id) {
        log.debug("Finding patient with id: '{}'", id);
        return patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Patient.class, "id", id));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Patient patientToDelete = findById(id);
        log.debug("Deleting patient with id: '{}'", id);
        patientRepository.delete(patientToDelete);
    }

    @Override
    @Transactional
    public Patient create(Patient patientToCreate) {
        return patientRepository.save(patientToCreate);
    }
}
