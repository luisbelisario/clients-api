package com.reservei.clientsapi.exception;

import com.reservei.clientsapi.exception.dto.ErrorMessageDto;
import lombok.Getter;

import java.io.Serial;

@Getter
public class GenericException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorMessageDto errorMessageDto;

    public GenericException(String message) {
        this.errorMessageDto = ErrorMessageDto.toDto(message);
    }
}
