package com.reservei.clientsapi.exception.dto;

import com.reservei.clientsapi.domain.dto.MessageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorMessageDto {

    private String errorMessage;

    public static ErrorMessageDto toDto(String message) {
        return new ErrorMessageDto(message);
    }
}
