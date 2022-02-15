package inc.evil.medassist.treatmentplan.model;

import inc.evil.medassist.common.jpa.AbstractEqualityCheckTest;
import inc.evil.medassist.doctor.model.Doctor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TreatmentPlanItemJpaTest extends AbstractEqualityCheckTest<TreatmentPlanItem> {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void equalsAndHashCodeShouldBeJpaCompliant() {
        TreatmentPlanItem item = TreatmentPlanItem.builder()
                .treatmentPlan(entityManager.find(TreatmentPlan.class, "ae3e4567-e89b-e2d3-a4e6-426e14174b0a"))
                .doctor(entityManager.find(Doctor.class, "22297b89-222a-4daa-222f-5995fd44da3e"))
                .description("Inspect the teeth")
                .status(TreatmentPlanItemStatus.SCHEDULED)
                .scheduledAt(LocalDate.of(2022, 5, 24))
                .createdAt(LocalDateTime.of(2021, 12, 13, 17, 0))
                .updatedAt(LocalDateTime.of(2021, 12, 13, 17, 0))
                .build();

        assertEqualityConsistency(TreatmentPlanItem.class, item);
    }
}
