package inc.evil.medassist.doctor.service;

import inc.evil.medassist.doctor.model.Doctor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface DoctorService {
    List<Doctor> findAll();

    Map<String, Long> countSpecialties();

    Doctor findById(String id);

    void deleteById(String id);

    Doctor update(String id, Doctor newDoctorState);

    Doctor create(Doctor patientToCreate);
}
