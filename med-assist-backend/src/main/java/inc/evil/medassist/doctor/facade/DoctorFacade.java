package inc.evil.medassist.doctor.facade;

import inc.evil.medassist.doctor.web.DoctorResponse;
import inc.evil.medassist.doctor.web.UpsertDoctorRequest;

import java.util.List;
import java.util.Map;

public interface DoctorFacade {
    List<DoctorResponse> findAll();

    Map<String, Long> countSpecialties();

    DoctorResponse findById(String id);

    DoctorResponse create(UpsertDoctorRequest request);

    void deleteById(String id);

    DoctorResponse update(String id, UpsertDoctorRequest request);
}
