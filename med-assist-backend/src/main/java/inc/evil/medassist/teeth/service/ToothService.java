package inc.evil.medassist.teeth.service;

import inc.evil.medassist.teeth.model.Tooth;

import java.util.List;
import java.util.Set;

public interface ToothService {
    Set<Tooth> findAllByIdsIn(List<String> ids);

    Set<Tooth> findAllByPatientId(String patientId);

    Tooth findById(String id);

    void markAsExtracted(List<String> teethIds);

    void markAsNotExtracted(List<String> teethIds);
}
