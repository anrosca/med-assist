package inc.evil.medassist.common.security;

import inc.evil.medassist.config.security.JwtTokenManager;
import inc.evil.medassist.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestSecurityConfiguration {
    @Bean
    public JwtTokenManager jwtTokenManager(@Value("${jwt.token.secret}") final String jwtTokenSecret,
                                           @Value("${jwt.token.issuer}") final String jwtTokenIssuer,
                                           @Value("${jwt.token.expiration.milliseconds}") final Long jwtTokenExpirationMilliseconds) {
        return new JwtTokenManager(jwtTokenSecret, jwtTokenIssuer, jwtTokenExpirationMilliseconds);
    }

    @Bean
    public UserRepository userRepository() {
        return mock(UserRepository.class);
    }
}
