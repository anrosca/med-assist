package inc.evil.medassist.treatmentplan.model;

import inc.evil.medassist.common.jpa.AbstractEqualityCheckTest;
import inc.evil.medassist.patient.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TreatmentPlanJpaTest extends AbstractEqualityCheckTest<TreatmentPlan> {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void equalsAndHashCodeShouldBeJpaCompliant() {
        TreatmentPlan treatmentPlan = TreatmentPlan.builder()
                .planName("Install braces")
                .patient(entityManager.find(Patient.class, "123e4567-e89b-12d3-a456-426614174000"))
                .createdAt(LocalDateTime.of(2021, 12, 13, 17, 0))
                .updatedAt(LocalDateTime.of(2021, 12, 13, 17, 0))
                .build();

        assertEqualityConsistency(TreatmentPlan.class, treatmentPlan);
    }
}
