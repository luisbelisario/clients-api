package com.reservei.clientsapi.exception;

import com.reservei.clientsapi.domain.dto.MessageDto;
import com.reservei.clientsapi.exception.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerController {

    @ExceptionHandler(EmailCadastradoException.class)
    public ResponseEntity<ErrorMessageDto> emailIntegrityViolationHandler(EmailCadastradoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorMessageDto());
    }

    @ExceptionHandler(CpfCadastradoException.class)
    public ResponseEntity<ErrorMessageDto> cpfIntegrityViolationHandler(CpfCadastradoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorMessageDto());
    }
}
