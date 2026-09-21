package com.server.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;

/**
 * Global handler so that invalid client input returns a clean 400 with a
 * readable message instead of a 500 with a stack trace, and so that
 * "lookup matched no row" is reported as a 404 rather than a server fault.
 *
 * <p>Handler selection note: Spring always picks the MOST SPECIFIC matching
 * handler. The dedicated 400 handlers below therefore keep winning for their own
 * exception types even though RuntimeException is their supertype, so adding the
 * RuntimeException handler only re-classifies what previously fell through to
 * the generic 500 handler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleUnreadable(HttpMessageNotReadableException ex) {
        return build(HttpStatus.BAD_REQUEST,
                "Invalid data format in request. Please check that numeric fields (HR Contact No, No. of Students) contain only numbers and dates are in valid format.");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return build(HttpStatus.BAD_REQUEST,
                "Invalid value for parameter '" + ex.getName() + "'. Expected type: "
                        + ex.getRequiredType().getSimpleName() + ".");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        StringBuilder msg = new StringBuilder();
        ex.getBindingResult().getFieldErrors()
                .forEach(fe -> msg.append(fe.getField()).append(": ").append(fe.getDefaultMessage()).append("; "));
        return build(HttpStatus.BAD_REQUEST, msg.toString());
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Map<String, Object>> handleNumberFormat(NumberFormatException ex) {
        return build(HttpStatus.BAD_REQUEST, "Numeric fields must contain valid numbers only.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Unmapped URL: Spring raises this as a 404 by design, but the generic
     * Exception handler below used to intercept it and turn it into a 500.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoResource(NoResourceFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "No such endpoint: /" + ex.getResourcePath());
    }

    /**
     * A lookup that matched no row is a client-meaning outcome, not a server
     * fault. Legacy services signal it with a bare
     * {@code RuntimeException("... not found")}, so map that to 404 and keep
     * every other RuntimeException as a genuine 500. This covers all modules
     * without changing any service.
     *
     * <p>Spring picks the most specific handler, so this does not affect the
     * dedicated 400 handlers above (IllegalArgumentException, NumberFormatException,
     * HttpMessageNotReadableException, MethodArgumentTypeMismatchException are all
     * RuntimeException subtypes that keep their own mappings).
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex) {
        String message = ex.getMessage() == null ? "" : ex.getMessage();

        if (message.toLowerCase().contains("not found")) {
            log.warn("Lookup returned no row, responding 404: {}", message);
            return build(HttpStatus.NOT_FOUND, message);
        }

        log.error("Unhandled RuntimeException", ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred while processing the request. Please verify the entered data and try again.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        log.error("Unhandled exception", ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred while processing the request. Please verify the entered data and try again.");
    }

    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(Map.of(
                        "status", status.value(),
                        "error", status.getReasonPhrase(),
                        "message", message));
    }
}