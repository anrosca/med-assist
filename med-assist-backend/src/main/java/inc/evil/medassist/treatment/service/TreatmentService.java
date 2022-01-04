package inc.evil.medassist.treatment.service;

import inc.evil.medassist.appointment.model.Appointment;
import inc.evil.medassist.treatment.model.Treatment;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface TreatmentService {
    List<Treatment> findAll();

    Treatment findById(String id);

    void deleteById(String id);

    Treatment create(Treatment treatmentToCreate);

    List<Treatment> findByDoctorId(String id);

    List<Treatment> findByPatientId(String id);

    List<Treatment> findByToothId(String toothId);
}
