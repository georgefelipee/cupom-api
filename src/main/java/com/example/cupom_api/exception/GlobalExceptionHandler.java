package com.example.cupom_api.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<ProblemResponse.ErrorDetail> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ProblemResponse.ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ProblemResponse body = ProblemResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation failed")
                .message("Erros de validacao encontrados")
                .errors(errors)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request
    ) {
        List<ProblemResponse.ErrorDetail> errors = ex.getConstraintViolations().stream()
                .map(v -> new ProblemResponse.ErrorDetail(v.getPropertyPath().toString(), v.getMessage()))
                .collect(Collectors.toList());

        ProblemResponse body = ProblemResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation failed")
                .message("Erros de validacao encontrados")
                .errors(errors)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemResponse> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        ProblemResponse body = ProblemResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Invalid request")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        ProblemResponse body = ProblemResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal server error")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private String formatFieldError(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }

    public static class ProblemResponse {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
        private List<ErrorDetail> errors;

        private ProblemResponse() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public int getStatus() {
            return status;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }

        public String getPath() {
            return path;
        }

        public List<ErrorDetail> getErrors() {
            return errors;
        }

        public static class Builder {
            private final ProblemResponse instance;

            private Builder() {
                this.instance = new ProblemResponse();
                this.instance.timestamp = LocalDateTime.now();
            }

            public Builder status(int status) {
                this.instance.status = status;
                return this;
            }

            public Builder error(String error) {
                this.instance.error = error;
                return this;
            }

            public Builder message(String message) {
                this.instance.message = message;
                return this;
            }

            public Builder path(String path) {
                this.instance.path = path;
                return this;
            }

            public Builder errors(List<ErrorDetail> errors) {
                this.instance.errors = errors;
                return this;
            }

            public ProblemResponse build() {
                return this.instance;
            }
        }

        public static class ErrorDetail {
            private final String field;
            private final String message;

            public ErrorDetail(String field, String message) {
                this.field = field;
                this.message = message;
            }

            public String getField() {
                return field;
            }

            public String getMessage() {
                return message;
            }
        }
    }
}
