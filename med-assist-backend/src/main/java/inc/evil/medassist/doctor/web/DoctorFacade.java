package inc.evil.medassist.doctor.web;

import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.doctor.service.DoctorService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DoctorFacade {
    private final DoctorService doctorService;

    public DoctorFacade(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public List<DoctorResponse> findAll() {
        return doctorService.findAll()
                .stream()
                .map(DoctorResponse::from)
                .toList();
    }

    public DoctorResponse findById(String id) {
        return DoctorResponse.from(doctorService.findById(id));
    }

    public void deleteById(String id) {
        doctorService.deleteById(id);
    }

    public DoctorResponse create(CreateDoctorRequest request) {
        Doctor createdDoctor = doctorService.create(request.toDoctor());
        return DoctorResponse.from(createdDoctor);
    }
}
