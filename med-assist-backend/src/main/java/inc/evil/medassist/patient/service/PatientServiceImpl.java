package inc.evil.medassist.patient.service;

import inc.evil.medassist.common.exception.NotFoundException;
import inc.evil.medassist.patient.model.Patient;
import inc.evil.medassist.teeth.model.Tooth;
import inc.evil.medassist.patient.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static inc.evil.medassist.teeth.model.ToothName.*;

@Service
@Slf4j
class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Patient> findAll() {
        log.debug("Finding all patients");
        return patientRepository.findAllNonDeleted();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> countAgeCategories() {
        final Map<String, Long> map = new TreeMap<>();
        patientRepository.countAgeCategories().forEach(e -> map.put(e.getCategory(), e.getCount()));
        return map;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> countPatientsCreatedPerMonth() {
        final Map<String, Long> map = new TreeMap<>();
        patientRepository.countPatientsCreatedPerMonth().forEach(e -> map.put(e.getMonth(), e.getCount()));
        return map;
    }

    @Override
    @Transactional(readOnly = true)
    public Patient findById(String id) {
        log.debug("Finding patient with id: '{}'", id);
        return patientRepository.findByIdNonDeleted(id)
                .orElseThrow(() -> new NotFoundException(Patient.class, "id", id));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Patient patientToDelete = findById(id);
        log.debug("Deleting patient with id: '{}'", id);
        patientToDelete.setDeleted(true);
    }

    @Override
    @Transactional
    public Patient create(Patient patientToCreate) {
        addTeeth(patientToCreate);
        return patientRepository.save(patientToCreate);
    }

    @Override
    @Transactional
    public Patient update(String id, Patient patientToUpdate) {
        Patient originalPatient = findById(id);
        Patient updatedPatient = originalPatient.mergeWith(patientToUpdate);
        return patientRepository.save(updatedPatient);
    }

    private void addTeeth(final Patient patientToCreate) {
        final List<Tooth> teeth = Arrays.stream(values())
                .map(t -> Tooth.builder().name(t).patient(patientToCreate).build()).collect(Collectors.toList());
        patientToCreate.setTeeth(teeth);
    }
}
