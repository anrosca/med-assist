package inc.evil.medassist.appointment.repository;

import inc.evil.medassist.appointment.model.Appointment;
import inc.evil.medassist.appointment.model.AppointmentColor;
import inc.evil.medassist.common.AbstractIntegrationTest;
import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.patient.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AppointmentRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenAppointmentStartsOneMinuteTooEarly_andOverlapsWithAnotherOne_shouldReturnConflictingAppointment() {
        String doctorId = "f23e4567-e89b-12d3-a456-426614174000";
        LocalDate appointmentDate = LocalDate.of(2021, 12, 12);
        LocalTime startTime = LocalTime.of(17, 59);
        LocalTime endTime = LocalTime.of(18, 30);
        Optional<Appointment> expectedConflictingAppointment = Optional.of(
                Appointment.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .doctor(entityManager.find(Doctor.class, "f23e4567-e89b-12d3-a456-426614174000"))
                        .patient(entityManager.find(Patient.class, "f44e4567-ef9c-12d3-a45b-52661417400a"))
                        .appointmentDate(LocalDate.of(2021, 12, 12))
                        .startTime(LocalTime.of(17, 0))
                        .endTime(LocalTime.of(18, 0))
                        .operation("Выдача каппы")
                        .createdAt(LocalDateTime.parse("2021-12-12T17:33:20.998582"))
                        .updatedAt(LocalDateTime.parse("2021-12-12T17:33:20.998582"))
                        .color(new AppointmentColor("#ff1f1f", "#D1E8FF"))
                        .build()
        );

        Optional<Appointment> conflictingAppointment =
                appointmentRepository.findConflictingAppointment(doctorId, appointmentDate, startTime, endTime, null);

        assertThat(conflictingAppointment).usingRecursiveComparison().isEqualTo(expectedConflictingAppointment);
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenAppointmentStartsAfterAnExistingOne_butTheyOverlap_shouldReturnConflictingAppointment() {
        String doctorId = "f23e4567-e89b-12d3-a456-426614174000";
        LocalDate appointmentDate = LocalDate.of(2021, 12, 12);
        LocalTime startTime = LocalTime.of(17, 5);
        LocalTime endTime = LocalTime.of(17, 30);
        Optional<Appointment> expectedConflictingAppointment = Optional.of(
                Appointment.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .doctor(entityManager.find(Doctor.class, "f23e4567-e89b-12d3-a456-426614174000"))
                        .patient(entityManager.find(Patient.class, "f44e4567-ef9c-12d3-a45b-52661417400a"))
                        .appointmentDate(LocalDate.of(2021, 12, 12))
                        .startTime(LocalTime.of(17, 0))
                        .endTime(LocalTime.of(18, 0))
                        .operation("Выдача каппы")
                        .createdAt(LocalDateTime.parse("2021-12-12T17:33:20.998582"))
                        .updatedAt(LocalDateTime.parse("2021-12-12T17:33:20.998582"))
                        .color(new AppointmentColor("#ff1f1f", "#D1E8FF"))
                        .build()
        );

        Optional<Appointment> conflictingAppointment =
                appointmentRepository.findConflictingAppointment(doctorId, appointmentDate, startTime, endTime, null);

        assertThat(conflictingAppointment).usingRecursiveComparison().isEqualTo(expectedConflictingAppointment);
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenAppointmentEndTimeOverlapsWithAnotherOne_shouldReturnConflictingAppointment() {
        String doctorId = "f23e4567-e89b-12d3-a456-426614174000";
        LocalDate appointmentDate = LocalDate.of(2021, 12, 12);
        LocalTime startTime = LocalTime.of(16, 30);
        LocalTime endTime = LocalTime.of(17, 30);
        Optional<Appointment> expectedConflictingAppointment = Optional.of(
                Appointment.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .doctor(entityManager.find(Doctor.class, "f23e4567-e89b-12d3-a456-426614174000"))
                        .patient(entityManager.find(Patient.class, "f44e4567-ef9c-12d3-a45b-52661417400a"))
                        .appointmentDate(LocalDate.of(2021, 12, 12))
                        .startTime(LocalTime.of(17, 0))
                        .endTime(LocalTime.of(18, 0))
                        .operation("Выдача каппы")
                        .createdAt(LocalDateTime.parse("2021-12-12T17:33:20.998582"))
                        .updatedAt(LocalDateTime.parse("2021-12-12T17:33:20.998582"))
                        .color(new AppointmentColor("#ff1f1f", "#D1E8FF"))
                        .build()
        );

        Optional<Appointment> conflictingAppointment =
                appointmentRepository.findConflictingAppointment(doctorId, appointmentDate, startTime, endTime, null);

        assertThat(conflictingAppointment).usingRecursiveComparison().isEqualTo(expectedConflictingAppointment);
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenSecondAppointmentIsAtTheSameTimeWithAnExistingOne_shouldReturnConflictingAppointment() {
        String doctorId = "f23e4567-e89b-12d3-a456-426614174000";
        LocalDate appointmentDate = LocalDate.of(2021, 12, 12);
        LocalTime startTime = LocalTime.of(17, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        Optional<Appointment> expectedConflictingAppointment = Optional.of(
                Appointment.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .doctor(entityManager.find(Doctor.class, "f23e4567-e89b-12d3-a456-426614174000"))
                        .patient(entityManager.find(Patient.class, "f44e4567-ef9c-12d3-a45b-52661417400a"))
                        .appointmentDate(LocalDate.of(2021, 12, 12))
                        .startTime(LocalTime.of(17, 0))
                        .endTime(LocalTime.of(18, 0))
                        .operation("Выдача каппы")
                        .createdAt(LocalDateTime.parse("2021-12-12T17:33:20.998582"))
                        .updatedAt(LocalDateTime.parse("2021-12-12T17:33:20.998582"))
                        .color(new AppointmentColor("#ff1f1f", "#D1E8FF"))
                        .build()
        );

        Optional<Appointment> conflictingAppointment =
                appointmentRepository.findConflictingAppointment(doctorId, appointmentDate, startTime, endTime, null);

        assertThat(conflictingAppointment).usingRecursiveComparison().isEqualTo(expectedConflictingAppointment);
    }

    @Test
    @Sql("/test-data/appointment/two-appointments.sql")
    public void whenThereIsAnotherAppointmentWhichOverlaps_shouldReturnIt() {
        String doctorId = "f23e4567-e89b-12d3-a456-426614174000";
        LocalDate appointmentDate = LocalDate.of(2021, 12, 12);
        LocalTime startTime = LocalTime.of(16, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        String appointmentId = "ba3e4567-e89b-12d3-b457-5267141750ab";
        Optional<Appointment> expectedConflictingAppointment = Optional.of(
                Appointment.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .doctor(entityManager.find(Doctor.class, "f23e4567-e89b-12d3-a456-426614174000"))
                        .patient(entityManager.find(Patient.class, "f44e4567-ef9c-12d3-a45b-52661417400a"))
                        .appointmentDate(LocalDate.of(2021, 12, 12))
                        .startTime(LocalTime.of(17, 0))
                        .endTime(LocalTime.of(18, 0))
                        .operation("Выдача каппы")
                        .createdAt(LocalDateTime.parse("2021-12-12T17:33:20.998582"))
                        .updatedAt(LocalDateTime.parse("2021-12-12T17:33:20.998582"))
                        .color(new AppointmentColor("#ff1f1f", "#D1E8FF"))
                        .build()
        );

        Optional<Appointment> conflictingAppointment =
                appointmentRepository.findConflictingAppointment(doctorId, appointmentDate, startTime, endTime, appointmentId);

        assertThat(conflictingAppointment).usingRecursiveComparison().isEqualTo(expectedConflictingAppointment);
    }

    @Test
    @Sql("/test-data/appointment/two-appointments.sql")
    public void whenThereNoOtherAppointmentsWhichOverlap_shouldReturnEmptyOptional() {
        String doctorId = "f23e4567-e89b-12d3-a456-426614174000";
        LocalDate appointmentDate = LocalDate.of(2021, 12, 12);
        LocalTime startTime = LocalTime.of(15, 0);
        LocalTime endTime = LocalTime.of(16, 0);
        String appointmentId = "ba3e4567-e89b-12d3-b457-5267141750ab";

        Optional<Appointment> conflictingAppointment =
                appointmentRepository.findConflictingAppointment(doctorId, appointmentDate, startTime, endTime, appointmentId);

        assertThat(conflictingAppointment).usingRecursiveComparison().isEqualTo(Optional.empty());
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenAppointmentsDontOverlap_shouldReturnEmptyOptional() {
        String doctorId = "f23e4567-e89b-12d3-a456-426614174000";
        LocalDate appointmentDate = LocalDate.of(2021, 12, 12);
        LocalTime startTime = LocalTime.of(16, 30);
        LocalTime endTime = LocalTime.of(16, 59);

        Optional<Appointment> conflictingAppointment =
                appointmentRepository.findConflictingAppointment(doctorId, appointmentDate, startTime, endTime, null);

        assertThat(conflictingAppointment).isEqualTo(Optional.empty());
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenAppointmentsAreInDifferentDays_shouldReturnEmptyOptional() {
        String doctorId = "f23e4567-e89b-12d3-a456-426614174000";
        LocalDate appointmentDate = LocalDate.of(2021, 12, 13);
        LocalTime startTime = LocalTime.of(17, 30);
        LocalTime endTime = LocalTime.of(18, 59);

        Optional<Appointment> conflictingAppointment =
                appointmentRepository.findConflictingAppointment(doctorId, appointmentDate, startTime, endTime, null);

        assertThat(conflictingAppointment).isEqualTo(Optional.empty());
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenDoctorDoesNotExist_shouldReturnEmptyOptional() {
        String doctorId = "1";
        LocalDate appointmentDate = LocalDate.of(2021, 12, 13);
        LocalTime startTime = LocalTime.of(17, 30);
        LocalTime endTime = LocalTime.of(18, 59);

        Optional<Appointment> conflictingAppointment =
                appointmentRepository.findConflictingAppointment(doctorId, appointmentDate, startTime, endTime, null);

        assertThat(conflictingAppointment).isEqualTo(Optional.empty());
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenThereAreNoAppointmentsForTheGivenDay_shouldReturnEmptyOptional() {
        String doctorId = "1";
        LocalDate appointmentDate = LocalDate.of(2021, 12, 10);
        LocalTime startTime = LocalTime.of(17, 30);
        LocalTime endTime = LocalTime.of(18, 59);

        Optional<Appointment> conflictingAppointment =
                appointmentRepository.findConflictingAppointment(doctorId, appointmentDate, startTime, endTime, null);

        assertThat(conflictingAppointment).isEqualTo(Optional.empty());
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToFindAppointments_forOneDayTimeRange() {
        String doctorId = "f23e4567-e89b-12d3-a456-426614174000";
        LocalDate startDate = LocalDate.of(2021, 12, 12);
        LocalDate endDate = LocalDate.of(2021, 12, 12);
        List<Appointment> expectedAppointments = List.of(
                Appointment.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .doctor(entityManager.find(Doctor.class, "f23e4567-e89b-12d3-a456-426614174000"))
                        .patient(entityManager.find(Patient.class, "f44e4567-ef9c-12d3-a45b-52661417400a"))
                        .appointmentDate(LocalDate.of(2021, 12, 12))
                        .startTime(LocalTime.of(17, 0))
                        .endTime(LocalTime.of(18, 0))
                        .operation("Выдача каппы")
                        .createdAt(LocalDateTime.parse("2021-12-12T17:33:20.998582"))
                        .updatedAt(LocalDateTime.parse("2021-12-12T17:33:20.998582"))
                        .color(new AppointmentColor("#ff1f1f", "#D1E8FF"))
                        .build()
        );

        List<Appointment> actualAppointments =
                appointmentRepository.findByDoctorIdAndTimeRange(doctorId, startDate, endDate);

        assertThat(actualAppointments).usingRecursiveComparison().isEqualTo(expectedAppointments);
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToFindAppointments_forOneMonthTimeRange() {
        String doctorId = "f23e4567-e89b-12d3-a456-426614174000";
        LocalDate startDate = LocalDate.of(2021, 11, 1);
        LocalDate endDate = LocalDate.of(2021, 12, 17);
        List<Appointment> expectedAppointments = List.of(
                Appointment.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .doctor(entityManager.find(Doctor.class, "f23e4567-e89b-12d3-a456-426614174000"))
                        .patient(entityManager.find(Patient.class, "f44e4567-ef9c-12d3-a45b-52661417400a"))
                        .appointmentDate(LocalDate.of(2021, 12, 12))
                        .startTime(LocalTime.of(17, 0))
                        .endTime(LocalTime.of(18, 0))
                        .operation("Выдача каппы")
                        .createdAt(LocalDateTime.parse("2021-12-12T17:33:20.998582"))
                        .updatedAt(LocalDateTime.parse("2021-12-12T17:33:20.998582"))
                        .color(new AppointmentColor("#ff1f1f", "#D1E8FF"))
                        .build()
        );

        List<Appointment> actualAppointments =
                appointmentRepository.findByDoctorIdAndTimeRange(doctorId, startDate, endDate);

        assertThat(actualAppointments).usingRecursiveComparison().isEqualTo(expectedAppointments);
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToFindAppointments_forOneWeekTimeRange() {
        String doctorId = "f23e4567-e89b-12d3-a456-426614174000";
        LocalDate startDate = LocalDate.of(2021, 12, 10);
        LocalDate endDate = LocalDate.of(2021, 12, 17);
        List<Appointment> expectedAppointments = List.of(
                Appointment.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .doctor(entityManager.find(Doctor.class, "f23e4567-e89b-12d3-a456-426614174000"))
                        .patient(entityManager.find(Patient.class, "f44e4567-ef9c-12d3-a45b-52661417400a"))
                        .appointmentDate(LocalDate.of(2021, 12, 12))
                        .startTime(LocalTime.of(17, 0))
                        .endTime(LocalTime.of(18, 0))
                        .operation("Выдача каппы")
                        .createdAt(LocalDateTime.parse("2021-12-12T17:33:20.998582"))
                        .updatedAt(LocalDateTime.parse("2021-12-12T17:33:20.998582"))
                        .color(new AppointmentColor("#ff1f1f", "#D1E8FF"))
                        .build()
        );

        List<Appointment> actualAppointments =
                appointmentRepository.findByDoctorIdAndTimeRange(doctorId, startDate, endDate);

        assertThat(actualAppointments).usingRecursiveComparison().isEqualTo(expectedAppointments);
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenThereAreNoAppointmentsInTimeRange_shouldReturnEmptyList() {
        String doctorId = "f23e4567-e89b-12d3-a456-426614174000";
        LocalDate startDate = LocalDate.of(2021, 12, 10);
        LocalDate endDate = LocalDate.of(2021, 12, 11);

        List<Appointment> actualAppointments =
                appointmentRepository.findByDoctorIdAndTimeRange(doctorId, startDate, endDate);

        assertThat(actualAppointments).isEmpty();
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenDoctorDoesNotExist_shouldReturnEmptyList() {
        String doctorId = "1";
        LocalDate startDate = LocalDate.of(2021, 12, 10);
        LocalDate endDate = LocalDate.of(2021, 12, 11);

        List<Appointment> actualAppointments =
                appointmentRepository.findByDoctorIdAndTimeRange(doctorId, startDate, endDate);

        assertThat(actualAppointments).isEmpty();
    }
}
