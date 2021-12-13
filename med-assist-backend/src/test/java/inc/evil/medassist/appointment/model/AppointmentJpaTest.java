package inc.evil.medassist.appointment.model;

import inc.evil.medassist.common.jpa.AbstractEqualityCheckTest;
import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.patient.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentJpaTest extends AbstractEqualityCheckTest<Appointment> {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void equalsAndHashCodeShouldBeJpaCompliant() {
        Appointment appointment = Appointment.builder()
                .appointmentDate(LocalDate.of(2021, 12, 10))
                .startTime(LocalTime.of(14, 55))
                .endTime(LocalTime.of(15, 30))
                .createdAt(LocalDateTime.of(2021, 12, 13, 17, 0))
                .updatedAt(LocalDateTime.of(2021, 12, 13, 17, 0))
                .details("New patient")
                .operation("Cleaning")
                .doctor(entityManager.find(Doctor.class, "15297b89-045a-4daa-998f-5995fd44da3e"))
                .patient(entityManager.find(Patient.class, "123e4567-e89b-12d3-a456-426614174000"))
                .build();

        assertEqualityConsistency(Appointment.class, appointment);
    }
}
