package inc.evil.medassist.appointment.facade;

import inc.evil.medassist.appointment.model.Appointment;
import inc.evil.medassist.appointment.service.AppointmentService;
import inc.evil.medassist.appointment.web.AppointmentResponse;
import inc.evil.medassist.appointment.web.UpsertAppointmentRequest;
import inc.evil.medassist.doctor.service.DoctorService;
import inc.evil.medassist.patient.model.Patient;
import inc.evil.medassist.patient.service.PatientService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
class AppointmentFacadeImpl implements AppointmentFacade {
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    public AppointmentFacadeImpl(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @Override
    public List<AppointmentResponse> findAll() {
        return appointmentService.findAll()
                .stream()
                .map(AppointmentResponse::from)
                .toList();
    }

    @Override
    public AppointmentResponse findById(String id) {
        return AppointmentResponse.from(appointmentService.findById(id));
    }

    @Override
    public void deleteById(String id) {
        appointmentService.deleteById(id);
    }

    @Override
    public AppointmentResponse create(UpsertAppointmentRequest request) {
        Appointment appointmentToCreate = toAppointment(request);
        Appointment createdAppointment = appointmentService.create(appointmentToCreate);
        return AppointmentResponse.from(createdAppointment);
    }

    @Override
    public List<AppointmentResponse> findAppointmentsByDoctorId(String doctorId) {
        return appointmentService.findByDoctorId(doctorId)
                .stream()
                .map(AppointmentResponse::from)
                .toList();
    }

    @Override
    public List<AppointmentResponse> findAppointmentsByDoctorIdInTimeRange(String doctorId, LocalDate startDate, LocalDate endDate) {
        return appointmentService.findByDoctorIdAndTimeRange(doctorId, startDate, endDate)
                .stream()
                .map(AppointmentResponse::from)
                .toList();
    }

    @Override
    public AppointmentResponse update(String id, UpsertAppointmentRequest request) {
        Appointment newAppointment = toAppointment(request);
        Appointment originalAppointment = appointmentService.findById(id);
        return AppointmentResponse.from(appointmentService.update(id, originalAppointment.mergeWith(newAppointment)));
    }

    private Appointment toAppointment(UpsertAppointmentRequest request) {
        return Appointment.builder()
                .appointmentDate(request.getAppointmentDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .operation(request.getOperation())
                .doctor(request.getDoctorId() != null ? doctorService.findById(request.getDoctorId()) : null)
                .patient(request.getPatientId() != null ? patientService.findById(request.getPatientId()) : null)
                .details(request.getDetails())
                .build();
    }

    @Override
    @Transactional
    public AppointmentResponse create(CreateAppointmentRequest request) {
        Appointment appointmentToCreate = Appointment.builder()
                .appointmentDate(request.getStartDate().toLocalDate())
                .startTime(request.getStartDate().toLocalTime())
                .endTime(request.getEndDate().toLocalTime())
                .operation(request.getOperation())
                .doctor(doctorService.findById(request.getDoctorId()))
                .details(request.getDetails())
                .build();
        if(request.isExistingPatient()){
            appointmentToCreate.setPatient(patientService.findById(request.getPatientId()));
        } else {
            final Patient patient = patientService.create(request.getPatientRequest().toPatient());
            appointmentToCreate.setPatient(patient);
        }
        Appointment createdAppointment = appointmentService.create(appointmentToCreate);
        return AppointmentResponse.from(createdAppointment);
    }

}
