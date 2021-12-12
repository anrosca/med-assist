package inc.evil.medassist.config.security;

import inc.evil.medassist.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenManager {

    private final String jwtTokenSecret;
    private final String jwtTokenIssuer;
    private final Long jwtTokenExpirationMilliseconds;

    public JwtTokenManager(@Value("${jwt.token.secret}") final String jwtTokenSecret,
                           @Value("${jwt.token.issuer}") final String jwtTokenIssuer,
                           @Value("${jwt.token.expiration.milliseconds}") final Long jwtTokenExpirationMilliseconds) {
        this.jwtTokenSecret = jwtTokenSecret;
        this.jwtTokenIssuer = jwtTokenIssuer;
        this.jwtTokenExpirationMilliseconds = jwtTokenExpirationMilliseconds;
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer(jwtTokenIssuer)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(new Date((new Date()).getTime() + jwtTokenExpirationMilliseconds))
                .signWith(SignatureAlgorithm.HS512, jwtTokenSecret)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(jwtTokenSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public LocalDate getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtTokenSecret)
                .parseClaimsJws(token)
                .getBody();

        return LocalDate.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtTokenSecret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

}
