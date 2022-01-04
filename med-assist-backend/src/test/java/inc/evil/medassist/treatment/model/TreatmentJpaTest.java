package inc.evil.medassist.treatment.model;

import inc.evil.medassist.common.jpa.AbstractEqualityCheckTest;
import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.patient.model.Patient;
import inc.evil.medassist.teeth.model.Tooth;
import inc.evil.medassist.teeth.model.ToothName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

class TreatmentJpaTest extends AbstractEqualityCheckTest<Treatment> {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void equalsAndHashCodeShouldBeJpaCompliant() {
        final Treatment treatment = Treatment.builder()
                .description("Cleaning")
                .price(250.00)
                .doctor(entityManager.find(Doctor.class, "15297b89-045a-4daa-998f-5995fd44da3e"))
                .patient(entityManager.find(Patient.class, "123e4567-e89b-12d3-a456-426614174000"))
                .createdAt(LocalDateTime.of(2021, 12, 13, 17, 0))
                .updatedAt(LocalDateTime.of(2021, 12, 13, 17, 0))
                .build();

        assertEqualityConsistency(Treatment.class, treatment);
    }
}
