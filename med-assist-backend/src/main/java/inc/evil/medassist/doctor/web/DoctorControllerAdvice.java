package inc.evil.medassist.doctor.web;

import inc.evil.medassist.common.error.ErrorResponse;
import inc.evil.medassist.doctor.service.DoctorNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes = DoctorController.class)
public class DoctorControllerAdvice {
    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ErrorResponse> onDoctorNotFound(DoctorNotFoundException e, HttpServletRequest request) {
        log.warn("Doctor not found", e);
        var errorMessages = List.of(e.getMessage());
        ErrorResponse errorModel = ErrorResponse.builder()
                .messages(errorMessages)
                .path(request.getServletPath())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorModel);
    }
}
