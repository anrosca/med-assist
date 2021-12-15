package inc.evil.medassist.doctor.service;

import inc.evil.medassist.common.exception.NotFoundException;

import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.doctor.repository.DoctorRepository;
import inc.evil.medassist.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.ValidationException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Doctor> findAll() {
        return doctorRepository.findAllWithAuthorities();
    }

    @Override
    @Transactional(readOnly = true)
    public Doctor findById(String id) {
        return doctorRepository.findByIdWithAuthorities(id)
                .orElseThrow(() -> new NotFoundException(Doctor.class, "id", id));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Doctor doctorToDelete = findById(id);
        doctorRepository.delete(doctorToDelete);
    }

    @Override
    @Transactional
    public Doctor update(final String id, final Doctor newDoctorState) {
        Doctor user = doctorRepository.findByIdWithAuthorities(id).orElseThrow(() -> new NotFoundException(Doctor.class, "id", id));
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
            user.setPassword(newDoctorState.getPassword());
        return user;
    }

    @Override
    @Transactional
    public Doctor create(Doctor doctorToCreate) {
        if (doctorRepository.findByUsername(doctorToCreate.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }
        doctorToCreate.setPassword(passwordEncoder.encode(doctorToCreate.getPassword()));
        return doctorRepository.save(doctorToCreate);
    }
}
