package com.reservei.clientsapi.exception;

import com.reservei.clientsapi.exception.dto.ErrorMessageDto;
import lombok.Getter;

import java.io.Serial;

@Getter
public class ApiCommunicationException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorMessageDto errorMessageDto;

    public ApiCommunicationException(String message) {
        this.errorMessageDto = ErrorMessageDto.toDto(message);
    }
}
