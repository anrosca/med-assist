package inc.evil.medassist.user.model;

import inc.evil.medassist.common.jpa.AbstractEqualityCheckTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserAuthorityJpaTest extends AbstractEqualityCheckTest<UserAuthority> {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void equalsAndHashCodeShouldBeJpaCompliant() {
        UserAuthority userAuthority = UserAuthority.builder()
                .authority(Authority.POWER_USER)
                .user(entityManager.find(User.class, "15297b89-045a-4daa-998f-5995fd44da3e"))
                .createdAt(LocalDateTime.of(2021, 12, 13, 17, 0))
                .updatedAt(LocalDateTime.of(2021, 12, 13, 17, 0))
                .build();

        assertEqualityConsistency(UserAuthority.class, userAuthority);
    }
}
