package inc.evil.medassist.user.model;

import inc.evil.medassist.common.jpa.AbstractEqualityCheckTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserJpaTest extends AbstractEqualityCheckTest<User> {
    @Test
    public void equalsAndHashCodeShouldBeJpaCompliant() {
        User user = User.builder()
                .firstName("Patrick")
                .lastName("Star")
                .username("patrick")
                .email("patrick-star@gmail.com")
                .password("passw0rd")
                .createdAt(LocalDateTime.of(2021, 12, 13, 17, 0))
                .updatedAt(LocalDateTime.of(2021, 12, 13, 17, 0))
                .build();

        assertEqualityConsistency(User.class, user);
    }
}
