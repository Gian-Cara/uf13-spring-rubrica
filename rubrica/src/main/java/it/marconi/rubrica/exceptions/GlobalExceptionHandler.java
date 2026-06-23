package it.marconi.rubrica.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import it.marconi.rubrica.dto.APIResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Gestisce gli errori dei campi di input (es. se manca il nome) -> Ritorna 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        APIResponse<Map<String, String>> response = new APIResponse<>("fail", errors, "Validazione fallita");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Gestisce il contatto non trovato -> Ritorna 404 Not Found
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<APIResponse<String>> handleNotFound(ResponseStatusException ex) {
        APIResponse<String> response = new APIResponse<>("fail", null, ex.getReason());
        return new ResponseEntity<>(response, ex.getStatusCode());
    }
}