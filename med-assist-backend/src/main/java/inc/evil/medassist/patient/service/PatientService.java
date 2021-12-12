package inc.evil.medassist.patient.service;

import inc.evil.medassist.patient.model.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> findAll();

    Patient findById(String id);

    void deleteById(String id);

    Patient create(Patient patientToCreate);
}
