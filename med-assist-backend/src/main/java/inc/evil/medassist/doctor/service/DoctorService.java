package inc.evil.medassist.doctor.service;

import inc.evil.medassist.doctor.model.Doctor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DoctorService {
    List<Doctor> findAll();

    Doctor findById(String id);

    void deleteById(String id);

    Doctor update(String id, Doctor newDoctorState);

    Doctor create(Doctor patientToCreate);
}
