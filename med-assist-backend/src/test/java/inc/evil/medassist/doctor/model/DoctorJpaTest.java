package inc.evil.medassist.doctor.model;

import inc.evil.medassist.common.jpa.AbstractEqualityCheckTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DoctorJpaTest extends AbstractEqualityCheckTest<Doctor> {
    @Test
    public void equalsAndHashCodeShouldBeJpaCompliant() {
        Doctor doctor = Doctor.builder()
                .firstName("Patrick")
                .lastName("Star")
                .username("patrick")
                .email("sponge-star@gmail.com")
                .telephoneNumber("3736932345")
                .specialty(Specialty.ORTHODONTIST)
                .createdAt(LocalDateTime.of(2021, 12, 13, 17, 0))
                .updatedAt(LocalDateTime.of(2021, 12, 13, 17, 0))
                .password("passw0rd")
                .build();

        assertEqualityConsistency(Doctor.class, doctor);
    }
}
