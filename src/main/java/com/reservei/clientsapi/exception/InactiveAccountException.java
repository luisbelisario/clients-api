package com.reservei.clientsapi.exception;

import com.reservei.clientsapi.exception.dto.ErrorMessageDto;
import lombok.Getter;

import java.io.Serial;

@Getter
public class InactiveAccountException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorMessageDto errorMessageDto;

    public InactiveAccountException(String message) {
        this.errorMessageDto = ErrorMessageDto.toDto(message);
    }
}
