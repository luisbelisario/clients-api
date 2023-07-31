package com.reservei.clientsapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageDto {

    private String message;

    public static MessageDto toDto(String message) {
        return new MessageDto(message);
    }
}
