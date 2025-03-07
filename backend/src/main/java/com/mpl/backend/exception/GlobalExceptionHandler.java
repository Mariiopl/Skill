package com.mpl.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones de validación, como los errores de las anotaciones de validación en los modelos.
     * @param ex Excepción de validación.
     * @param request Información de la solicitud.
     * @return ResponseEntity con los errores de validación.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        // Crear una lista de mensajes de error sin los nombres de los campos
        List<String> errorMessages = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones generales de tipo RuntimeException.
     * @param ex Excepción generada.
     * @param request Información de la solicitud.
     * @return ResponseEntity con los detalles del error.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetails> handleRuntimeException(RuntimeException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja excepciones generales.
     * @param ex Excepción generada.
     * @param request Información de la solicitud.
     * @return ResponseEntity con los detalles del error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
