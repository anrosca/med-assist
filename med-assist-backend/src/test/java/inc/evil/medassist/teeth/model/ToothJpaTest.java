package inc.evil.medassist.teeth.model;

import inc.evil.medassist.common.jpa.AbstractEqualityCheckTest;
import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.patient.model.Patient;
import inc.evil.medassist.treatment.model.Treatment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ToothJpaTest extends AbstractEqualityCheckTest<Tooth> {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void equalsAndHashCodeShouldBeJpaCompliant() {
        final Tooth tooth = Tooth.builder()
                .patient(entityManager.find(Patient.class, "123e4567-e89b-12d3-a456-426614174000"))
                .extracted(false)
                .treatments(Set.of(entityManager.find(Treatment.class, "ff01f8ab-2ce9-46db-87be-a00142830a05")))
                .name(ToothName.UR2)
                .createdAt(LocalDateTime.of(2021, 12, 13, 17, 0))
                .updatedAt(LocalDateTime.of(2021, 12, 13, 17, 0)).build();


        assertEqualityConsistency(Tooth.class, tooth);
    }
}
