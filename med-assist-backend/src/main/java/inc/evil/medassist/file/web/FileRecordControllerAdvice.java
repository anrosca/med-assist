package inc.evil.medassist.file.web;

import inc.evil.medassist.common.error.ErrorResponse;
import inc.evil.medassist.file.facade.InvalidFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes = FileRecordController.class)
public class FileRecordControllerAdvice {

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<ErrorResponse> onInvalidFileException(InvalidFileException e, HttpServletRequest request) {
        log.warn("Invalid file: {}", e.getMessage());
        var errorMessages = Set.of(e.getMessage());
        ErrorResponse errorModel = ErrorResponse.builder()
                .messages(errorMessages)
                .path(request.getServletPath())
                .build();
        return ResponseEntity.badRequest()
                .body(errorModel);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> onHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e, HttpServletRequest request) {
        log.warn("Invalid file: {}", e.getMessage());
        var errorMessages = Set.of(e.getMessage());
        ErrorResponse errorModel = ErrorResponse.builder()
                .messages(errorMessages)
                .path(request.getServletPath())
                .build();
        return ResponseEntity.badRequest()
                .body(errorModel);
    }
}
