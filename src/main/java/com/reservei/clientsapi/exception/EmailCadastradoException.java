package com.reservei.clientsapi.exception;

import com.reservei.clientsapi.domain.dto.MessageDto;
import com.reservei.clientsapi.exception.dto.ErrorMessageDto;
import lombok.Getter;

@Getter
public class EmailCadastradoException extends Exception {

    private static final long serialVersionUID = 1L;

    private final ErrorMessageDto errorMessageDto;

    public EmailCadastradoException(String message) {
        this.errorMessageDto = ErrorMessageDto.toDto(message);
    }
}
