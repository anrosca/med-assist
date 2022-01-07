package inc.evil.medassist.appointment.service;

import inc.evil.medassist.appointment.model.Appointment;
import inc.evil.medassist.appointment.repository.AppointmentRepository;
import inc.evil.medassist.common.exception.NotFoundException;
import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.doctor.repository.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
@Slf4j
class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> findAll() {
        return appointmentRepository.findAppointmentsWithDoctorsAndPatients();
    }

    @Override
    @Transactional(readOnly = true)
    public Long countAll() {
        return appointmentRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> countOperations() {
        Map<String, Long> map = new TreeMap<>();
        appointmentRepository.countOperations().forEach(e -> map.put(e.getOperation(), e.getCount()));
        return map;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> countAppointmentsPerMonth() {
        Map<String, Long> map = new TreeMap<>();
        appointmentRepository.countAppointmentsPerMonth().forEach(e -> map.put(e.getMonth(), e.getCount()));
        return map;
    }

    @Override
    @Transactional(readOnly = true)
    public Appointment findById(String id) {
        return appointmentRepository.findByIdWithDoctorsAndPatients(id)
                .orElseThrow(() -> new NotFoundException(Appointment.class, "id", id));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        log.debug("Deleting appointment with id: '{}'", id);
        Appointment appointmentToDelete = findById(id);
        appointmentRepository.delete(appointmentToDelete);
    }

    @Override
    @Transactional
    public Appointment create(Appointment appointmentToCreate) {
        checkForConflicts(appointmentToCreate);
        return appointmentRepository.save(appointmentToCreate);
    }

    private void checkForConflicts(Appointment appointmentToCreate) {
        LocalDate appointmentDate = appointmentToCreate.getAppointmentDate();
        LocalTime startTime = appointmentToCreate.getStartTime();
        LocalTime endTime = appointmentToCreate.getEndTime();
        String doctorId = appointmentToCreate.getDoctor().getId();
        Doctor doctor = doctorRepository.findEnabledByIdAndLock(doctorId);
        Optional<Appointment> conflictingAppointment =
                appointmentRepository.findConflictingAppointment(doctorId, appointmentDate, startTime, endTime, appointmentToCreate.getId());
        conflictingAppointment.ifPresent(overlappingAppointment -> {
            throw new ConflictingAppointmentsException("Doctor " + doctor.getFirstName() + " " + doctor.getLastName() +
                    " has already an appointment, starting from " + overlappingAppointment.getStartTime() +
                    " till " + overlappingAppointment.getEndTime());
        });
    }

    @Override
    @Transactional
    public Appointment update(String id, Appointment newAppointment) {
        checkForConflicts(newAppointment);
        appointmentRepository.save(newAppointment);
        return newAppointment;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> findByDoctorId(String id) {
        return appointmentRepository.findByDoctorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> findByDoctorIdAndTimeRange(String id, LocalDate startDate, LocalDate endDate) {
        return appointmentRepository.findByDoctorIdAndTimeRange(id, startDate, endDate);
    }
}
