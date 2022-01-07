package inc.evil.medassist.doctor.service;

import inc.evil.medassist.common.exception.NotFoundException;

import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.doctor.repository.DoctorRepository;
import inc.evil.medassist.treatment.service.TreatmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.ValidationException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final TreatmentService treatmentService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Doctor> findAll() {
        return doctorRepository.findAllEnabledWithAuthorities();
    }

    @Override
    @Transactional(readOnly = true)
    public Doctor findById(String id) {
        return doctorRepository.findEnabledByIdWithAuthorities(id)
                .orElseThrow(() -> new NotFoundException(Doctor.class, "id", id));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Doctor doctorToDelete = findById(id);
        doctorToDelete.setEnabled(false);
        log.debug("Deleting doctor with id: '{}'", id);
    }

    @Override
    @Transactional
    public Doctor update(final String id, final Doctor newDoctorState) {
        Doctor user = doctorRepository.findEnabledByIdWithAuthorities(id).orElseThrow(() -> new NotFoundException(Doctor.class, "id", id));
        if (newDoctorState.getFirstName() != null)
            user.setFirstName(newDoctorState.getFirstName());
        if (newDoctorState.getLastName() != null)
            user.setLastName(newDoctorState.getLastName());
        if (newDoctorState.getEmail() != null)
            user.setEmail(newDoctorState.getEmail());
        if (newDoctorState.getUsername() != null)
            user.setUsername(newDoctorState.getUsername());
        if (newDoctorState.getSpecialty() != null)
            user.setSpecialty(newDoctorState.getSpecialty());
        if (newDoctorState.getTelephoneNumber() != null)
            user.setTelephoneNumber(newDoctorState.getTelephoneNumber());
        if (!CollectionUtils.isEmpty(newDoctorState.getAuthorities())) {
            user.getAuthorities().clear();
            newDoctorState.getAuthorities().forEach(user::addAuthority);
        }
        if (newDoctorState.getPassword() != null)
            user.setPassword(passwordEncoder.encode(newDoctorState.getPassword()));
        return user;
    }

    @Override
    @Transactional
    public Doctor create(Doctor doctorToCreate) {
        if (doctorRepository.findEnabledByUsername(doctorToCreate.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }
        doctorToCreate.setPassword(passwordEncoder.encode(doctorToCreate.getPassword()));
        return doctorRepository.save(doctorToCreate);
    }
}
