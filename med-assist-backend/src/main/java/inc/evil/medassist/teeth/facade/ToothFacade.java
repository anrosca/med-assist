package inc.evil.medassist.teeth.facade;

import inc.evil.medassist.teeth.web.ToothResponse;

import java.util.List;

public interface ToothFacade {
    ToothResponse findById(String id);
    List<ToothResponse> findAllByPatientId(String patientId);
}
