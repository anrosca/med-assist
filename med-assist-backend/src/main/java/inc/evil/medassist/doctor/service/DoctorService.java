package inc.evil.medassist.doctor.service;

import inc.evil.medassist.doctor.model.Doctor;

import java.util.List;

public interface DoctorService {
    List<Doctor> findAll();

    Doctor findById(String id);

    void deleteById(String id);

    Doctor create(Doctor doctorToCreate);
}
