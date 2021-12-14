package inc.evil.medassist.appointment.web;

import inc.evil.medassist.appointment.service.ConflictingAppointmentsException;
import inc.evil.medassist.common.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes = AppointmentController.class)
public class AppointmentControllerAdvice {

    @ExceptionHandler(ConflictingAppointmentsException.class)
    public ResponseEntity<ErrorResponse> onConflictingAppointmentsException(ConflictingAppointmentsException e, HttpServletRequest request) {
        log.warn("Cannot create appointment: {}", e.getMessage());
        var errorMessages = Set.of(e.getMessage());
        ErrorResponse errorModel = ErrorResponse.builder()
                .messages(errorMessages)
                .path(request.getServletPath())
                .build();
        return ResponseEntity.badRequest()
                .body(errorModel);
    }
}
