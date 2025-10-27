package net.byteboost.junipy.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> handleInvalidFormat(InvalidFormatException ex) {
        // Check if it’s caused by an Enum
        if (ex.getTargetType().isEnum()) {
            String enumType = ex.getTargetType().getSimpleName();
            String invalidValue = ex.getValue().toString();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid value '" + invalidValue + "' for enum type " + enumType);
        }

        // For other invalid formats (like bad date formats)
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid value in request: " + ex.getValue());
    }
}
