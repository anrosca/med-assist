package inc.evil.medassist.patient.model;

import inc.evil.medassist.common.jpa.AbstractEqualityCheckTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PatientJpaTest extends AbstractEqualityCheckTest<Patient> {
    @Test
    public void equalsAndHashCodeShouldBeJpaCompliant() {
        Patient patient = Patient.builder()
                .firstName("Jim")
                .lastName("Morrison")
                .birthDate(LocalDate.of(1994, 10, 12))
                .phoneNumber("+37369985214")
                .createdAt(LocalDateTime.of(2021, 12, 13, 17, 0))
                .updatedAt(LocalDateTime.of(2021, 12, 13, 17, 0))
                .isDeleted(false)
                .build();

        assertEqualityConsistency(Patient.class, patient);
    }
}
