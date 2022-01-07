package inc.evil.medassist.patient.service;

import inc.evil.medassist.patient.model.Patient;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface PatientService {
    List<Patient> findAll();

    Map<String, Long> countAgeCategories();

    Map<String, Long> countPatientsCreatedPerMonth();

    Patient findById(String id);

    void deleteById(String id);

    Patient create(Patient patientToCreate);
}
