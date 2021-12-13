package inc.evil.medassist.user.web;

import inc.evil.medassist.common.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes = AuthenticationController.class)
public class AuthenticationControllerAdvice {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> onBadCredentialsException(BadCredentialsException e, HttpServletRequest request) {
        log.warn("Authentication failed", e);
        var errorMessages = Set.of(e.getMessage());
        ErrorResponse errorModel = ErrorResponse.builder()
                .messages(errorMessages)
                .path(request.getServletPath())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(errorModel);
    }
}
