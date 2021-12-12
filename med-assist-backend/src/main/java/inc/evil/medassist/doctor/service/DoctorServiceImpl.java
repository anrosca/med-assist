package inc.evil.medassist.doctor.service;

import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.doctor.repository.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Doctor> findAll() {
        log.debug("Finding all doctors");
        return doctorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Doctor findById(String id) {
        log.debug("Finding doctor with id: '{}'", id);
        return doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("No doctor with id: '" + id + "' was found."));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Doctor doctorToDelete = findById(id);
        log.debug("Deleting doctor with id: '{}'", id);
        doctorRepository.delete(doctorToDelete);
    }

    @Override
    @Transactional
    public Doctor create(Doctor doctorToCreate) {
        return doctorRepository.save(doctorToCreate);
    }
}
