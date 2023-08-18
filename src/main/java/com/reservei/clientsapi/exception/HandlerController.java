package com.reservei.clientsapi.exception;

import com.reservei.clientsapi.exception.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerController {

    @ExceptionHandler(EmailRegisteredException.class)
    public ResponseEntity<ErrorMessageDto> emailIntegrityViolationHandler(EmailRegisteredException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorMessageDto());
    }

    @ExceptionHandler(CpfRegisteredException.class)
    public ResponseEntity<ErrorMessageDto> cpfIntegrityViolationHandler(CpfRegisteredException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorMessageDto());
    }

    @ExceptionHandler(PublicIdRegisteredException.class)
    public ResponseEntity<ErrorMessageDto> cpfIntegrityViolationHandler(PublicIdRegisteredException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorMessageDto());
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> clienteNaoEncontradoHandler(ClientNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorMessageDto());
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorMessageDto> genericHandler(GenericException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorMessageDto());
    }

    @ExceptionHandler(InactiveAccountException.class)
    public ResponseEntity<ErrorMessageDto> inactiveAccountExceptionHandler(InactiveAccountException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorMessageDto());
    }

    @ExceptionHandler(ApiCommunicationException.class)
    public ResponseEntity<ErrorMessageDto> inactiveAccountExceptionHandler(ApiCommunicationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getErrorMessageDto());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorMessageDto> invalidTokentExceptionHandler(InvalidTokenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getErrorMessageDto());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<String> invalidTokentExceptionHandler(MissingRequestHeaderException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Não foi informado o token JWT");
    }
}
