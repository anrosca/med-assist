package inc.evil.medassist.doctor.facade;

import inc.evil.medassist.doctor.service.DoctorService;
import inc.evil.medassist.doctor.web.DoctorResponse;
import inc.evil.medassist.doctor.web.UpsertDoctorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DoctorFacadeImpl implements DoctorFacade {

    private final DoctorService doctorService;

    @Override
    public List<DoctorResponse> findAll() {
        return doctorService.findAll().stream().map(DoctorResponse::from).collect(Collectors.toList());
    }

    @Override
    public DoctorResponse findById(final String id) {
        return DoctorResponse.from(doctorService.findById(id));
    }

    @Override
    public DoctorResponse create(final UpsertDoctorRequest request) {
        return DoctorResponse.from(doctorService.create(request.toDoctor()));
    }

    @Override
    public void deleteById(final String id) {
        doctorService.deleteById(id);
    }

    @Override
    public DoctorResponse update(final String id, final UpsertDoctorRequest request) {
        return DoctorResponse.from(doctorService.update(id, request.toDoctor()));
    }
}
